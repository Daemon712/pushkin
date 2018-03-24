package io.blockchain.pushkin.service.impl;

import io.blockchain.pushkin.dto.Report;
import io.blockchain.pushkin.dto.UserRating;
import io.blockchain.pushkin.model.SpeechPart;
import io.blockchain.pushkin.repo.MessageEntityRepository;
import io.blockchain.pushkin.repo.WordUsageRepository;
import io.blockchain.pushkin.service.api.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    private static final List<SpeechPart> MEANINGFUL_SPEECH_PARTS = Arrays.asList(SpeechPart.adjective, SpeechPart.adverb, SpeechPart.verb, SpeechPart.noun);
    private WordUsageRepository wordUsageRepository;
    private MessageEntityRepository messageEntityRepository;

    @Override
    public Report buildUserReport(Integer userId) {
        Report report = new Report();
        report.setReportDateTime(LocalDateTime.now());
        report.setUniqueWords(wordUsageRepository.findDistinctWordByMessageUserId(userId).size());
        report.setTotalWords(wordUsageRepository.countByMessageUserId(userId));
        report.setRating(normalizeRating(wordUsageRepository.averageWordsRatingByMessageUserId(userId, MEANINGFUL_SPEECH_PARTS).orElse(0d)));
        report.setRating(wordUsageRepository.averageWordsRatingByMessageUserId(userId, MEANINGFUL_SPEECH_PARTS).orElse(0d));
        report.setErrorFrequency(messageEntityRepository.averageLiteracy(userId).orElse(0.0));
        return report;
    }

    @Override
    public List<UserRating> buildChatRatingReport(Long chatId) {
        return wordUsageRepository.calcUserRatingByChat(chatId, MEANINGFUL_SPEECH_PARTS)
                .stream()
                .peek(ur -> ur.setRating(normalizeRating(ur.getRating())))
                .collect(Collectors.toList());
    }

    @Autowired
    public void setWordUsageRepository(WordUsageRepository wordUsageRepository) {
        this.wordUsageRepository = wordUsageRepository;
    }

    private Double normalizeRating(Double rating){
        return 1000d / rating;
    }

    @Autowired
    public void setMessageEntityRepository(MessageEntityRepository messageEntityRepository) {
        this.messageEntityRepository = messageEntityRepository;
    }
}
