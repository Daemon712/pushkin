package io.blockchain.pushkin;

import io.blockchain.pushkin.model.GlobalDict;
import io.blockchain.pushkin.model.SpeechPart;
import io.blockchain.pushkin.model.Word;
import io.blockchain.pushkin.repo.GlobalDictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GlobalDictUtils {
    private GlobalDictRepository globalDictRepository;

    @PostConstruct
    public void initDict(){
        if (globalDictRepository.count() == 0){
            fillDictInitialData();
        }
    }

    private void fillDictInitialData(){
        try (Stream<String> lines = Files.lines(Paths.get("src", "main", "resources", "lemma.txt"), Charset.forName("windows-1251"))) {
            List<GlobalDict> items = lines.map(l -> l.split(" "))
                    .map(s -> {
                        GlobalDict item = new GlobalDict();
                        item.setWord(new Word(s[2], resolveSpeechPart(s[2], s[3])));
                        Double rate = Double.valueOf(s[1]);
                        item.setRate(rate);
                        item.setCount(rate.longValue());
                        return item;
                    })
                    .collect(Collectors.toList());
            globalDictRepository.saveAll(items);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SpeechPart resolveSpeechPart(String word, String speechPart) {
        switch (speechPart){
            case "ord": return SpeechPart.numeral_adjective;
            case "adv": return SpeechPart.adverb;
            case "adjpron": return SpeechPart.pronoun_adjective;
            case "pron":
                if (word.endsWith("й")) {
                    return SpeechPart.pronoun_adjective;
                } else if (word.endsWith("о") || word.endsWith("е")){
                    return SpeechPart.pronominal_adverb;
                } else {
                    return SpeechPart.pronoun_noun;
                }
            case "adj": return SpeechPart.adjective;
            case "verb": return SpeechPart.verb;
            case "prep": return SpeechPart.pretext;
            case "noun": return SpeechPart.noun;
            case "card": return SpeechPart.numeral;
            case "misc": return SpeechPart.particle;
            default: return null;
        }
    }

    @Autowired
    public void setGlobalDictRepository(GlobalDictRepository globalDictRepository) {
        this.globalDictRepository = globalDictRepository;
    }
}
