package io.blockchain.pushkin.service.impl;

import io.blockchain.pushkin.dto.Document;
import io.blockchain.pushkin.dto.Report;
import io.blockchain.pushkin.dto.UserRating;
import io.blockchain.pushkin.model.SpeechPart;
import io.blockchain.pushkin.model.Word;
import io.blockchain.pushkin.repo.MessageEntityRepository;
import io.blockchain.pushkin.repo.WordUsageRepository;
import io.blockchain.pushkin.service.api.KeyWordService;
import io.blockchain.pushkin.service.api.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    private static final List<SpeechPart> MEANINGFUL_SPEECH_PARTS =
            Arrays.asList(SpeechPart.adjective, SpeechPart.adverb, SpeechPart.verb, SpeechPart.noun);
    private final WordUsageRepository wordUsageRepository;
    private final MessageEntityRepository messageEntityRepository;
    private final KeyWordService keyWordService;

    @Autowired
    public ReportServiceImpl(WordUsageRepository wordUsageRepository,
                             MessageEntityRepository messageEntityRepository,
                             KeyWordService keyWordService) {
        this.wordUsageRepository = wordUsageRepository;
        this.messageEntityRepository = messageEntityRepository;
        this.keyWordService = keyWordService;
    }

    @Override
    public Report buildUserReport(Integer userId) {
        Report report = new Report();
        report.setReportDateTime(LocalDateTime.now());
        report.setUniqueWords(wordUsageRepository.findDistinctWordByMessageUserId(userId).size());
        report.setTotalWords(wordUsageRepository.countByMessageUserUserId(userId));
        report.setRating(normalizeRating(wordUsageRepository.averageWordsRatingByMessageUserId(userId).orElse(0d)));
        report.setLiteracy(messageEntityRepository.averageLiteracy(userId).orElse(0.0));
        return report;
    }

    @Override
    public Report buildChatReport(Long chatId) {
        Report report = new Report();
        report.setReportDateTime(LocalDateTime.now());
        List<Document> documentsForChat = wordUsageRepository.findDocumentsByChatId(chatId);

        Map<Integer, List<Word>> documents = new HashMap<>();
        documentsForChat.forEach(d -> {
            List<Word> userWords = documents.get(d.getUserId());
            if (userWords == null) {
                userWords = new ArrayList<>();
            }
            userWords.add(d.getWord());
            documents.put(d.getUserId(), userWords);
        });
        Map<Integer, Map<Word, Double>> tfidfMap = keyWordService.calcKeyWords(documents);

        //TODO implement Report building
        tfidfMap.forEach((k, v) -> {
            Map<Word, Double> wordDoubleMap = sortByValue(v);
            int[] top = new int[]{0};
            System.out.println("***************************************\n\n");
            System.out.println("TFIDF for " + k);
            wordDoubleMap.forEach((w, tf) -> {
                if (top[0] > 10) {
                    return;
                }
                System.out.println("WORD " + w + " TFIDF: " + tf);
                top[0] = top[0] + 1;
            });
        });
        return report;
    }

    private <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    @Override
    public List<UserRating> buildChatRatingReport(Long chatId) {
        return messageEntityRepository.findActiveChatUsers(chatId)
                .stream()
                .map(user -> {
                    List<Integer> messages = messageEntityRepository.find100LastMessages(chatId, user.getUserId());
                    Double rating = wordUsageRepository.findWordUsagesRating(chatId, messages)
                            .stream()
                            .limit(15)
                            .mapToDouble(Double::doubleValue)
                            .sorted()
                            .average()
                            .orElse(0);

                    return new UserRating(user, normalizeRating(rating));
                })
                .sorted(Comparator.comparing(UserRating::getRating).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<UserRating> buildChatLiteracyReport(Long chatId) {
        return messageEntityRepository.calcUserLiteracyByChat(chatId);
    }

    private Double normalizeRating(Double rating) {
        return 10 / (1 + Math.exp(-1 / rating));
    }
}
