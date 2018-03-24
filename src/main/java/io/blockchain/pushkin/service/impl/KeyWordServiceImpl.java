package io.blockchain.pushkin.service.impl;

import io.blockchain.pushkin.model.Word;
import io.blockchain.pushkin.service.api.KeyWordService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KeyWordServiceImpl implements KeyWordService {

    /**
     * TF-IDF
     */
    @Override
    public Map<Integer, Map<Word, Double>> calcKeyWords(Map<Integer, List<Word>> documents) {
        Map<Integer, Map<Word, Double>> tfIdfMap = new HashMap<>();
        documents.forEach((k, v) -> {
            Map<Word, Double> userTfIdfMap = new HashMap<>();
            Map<Word, Double> tf = calcTf(v);
            v.forEach(w -> {
                double idf = calcIDF(w, documents);
                double tfIdf = tf.get(w) * idf;
                userTfIdfMap.put(w, tfIdf);
            });
            tfIdfMap.put(k, userTfIdfMap);
        });
        return tfIdfMap;
    }

    private Map<Word, Double> calcTf(List<Word> userWords) {
        Map<Word, Integer> freq = new HashMap<>();
        userWords.forEach(w -> {
            Integer num = freq.get(w);
            freq.put(w, num == null ? 1 : num + 1);
        });
        Map<Word, Double> tf = new HashMap<>();
        freq.forEach((k, v) -> tf.put(k, freq.get(k) / (double) freq.size()));
        return tf;
    }

    private double calcIDF(Word word, Map<Integer, List<Word>> documents) {
        return Math.log10(documents.size() / documents.values().stream()
                .filter(lw -> lw.contains(word))
                .count());
    }
}
