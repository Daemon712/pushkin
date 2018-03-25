package io.blockchain.pushkin.service.impl;

import io.blockchain.pushkin.dto.Advice;
import io.blockchain.pushkin.model.*;
import io.blockchain.pushkin.repo.GlobalDictRepository;
import io.blockchain.pushkin.repo.MessageEntityRepository;
import io.blockchain.pushkin.repo.WordUsageRepository;
import io.blockchain.pushkin.service.api.AdviceService;
import io.blockchain.pushkin.service.api.SynonymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdviceHandlerImpl implements AdviceService {
    private final SynonymService synonymService;
    private final MessageEntityRepository messageEntityRepository;
    private final WordUsageRepository wordUsageRepository;
    private final GlobalDictRepository globalDictRepository;

    @Autowired
    public AdviceHandlerImpl(@Qualifier("remote") SynonymService synonymService,
                             MessageEntityRepository messageEntityRepository,
                             WordUsageRepository wordUsageRepository,
                             GlobalDictRepository globalDictRepository) {
        this.synonymService = synonymService;
        this.messageEntityRepository = messageEntityRepository;
        this.wordUsageRepository = wordUsageRepository;
        this.globalDictRepository = globalDictRepository;
    }

    @Override
    public Advice getAdvice(Integer userId) {
        Advice advice = new Advice();
        List<MessageEntity> lst = messageEntityRepository.findTop200ByUserUserIdOrderByDateDesc(userId);
        List<Word> words = getTotalWords(lst);
        Map<Word, Integer> freq = calcWordFrequencies(words);
        freq.forEach((k, v) -> {
            // if freq > 5%
            if (v > words.size() * 0.1) {
                advice.addRecommendation(buildRecommendation(k));
            }
            //TODO add checking with global dict
            Optional<GlobalDict> byId = globalDictRepository.findById(k);
//            if (byId.isPresent()) {
//                Double rate = byId.get().getRate();
//                System.out.println("For Word " + k.getWord() + " ABS FREQ=" + v + " Rate=" + rate);
//            }
        });
        if (advice.isEmpty()) {
            advice.addRecommendation("Я не нашел замечаний, Вы молодец:)");
        }
        return advice;
    }

    private List<Word> getTotalWords(List<MessageEntity> lst) {
        List<Word> words = new ArrayList<>();
        lst.forEach(me -> {
            List<WordUsage> wordUsages = wordUsageRepository.findWordUsagesByMessage(me);
            wordUsages.forEach(wu -> words.add(wu.getWord()));
        });
        return words;
    }

    private Map<Word, Integer> calcWordFrequencies(List<Word> words) {
        Map<Word, Integer> freq = new HashMap<>();
        words.forEach(w -> {
            Integer num = freq.get(w);
            if (num == null) {
                num = 0;
            }
            freq.put(w, num + 1);
        });
        return freq;
    }

    private String buildRecommendation(Word word) {
        if (SpeechPart.adjective.equals(word.getSpeechPart()) ||
                SpeechPart.noun.equals(word.getSpeechPart()) ||
                SpeechPart.verb.equals(word.getSpeechPart())) {
            List<String> synonyms = synonymService.getSynonyms(word.getWord());
            if (synonyms.isEmpty()) {
                return "Вы злоупотребляете словом *" + word.getWord() + "*. Попробуйте использовать его меньше. ";
            } else {
                return "Вы злоупотребляете словом *" + word.getWord() +
                        "*. Попробуйте использовать вместо него синоним: " + synonyms + ".";
            }
        } else {
            return "Вы злоупотребляете словом *" + word.getWord() + "*. Попробуйте использовать его меньше. ";
        }
    }
}
