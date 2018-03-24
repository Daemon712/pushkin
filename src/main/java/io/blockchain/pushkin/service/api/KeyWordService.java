package io.blockchain.pushkin.service.api;

import io.blockchain.pushkin.model.Word;

import java.util.List;
import java.util.Map;

public interface KeyWordService {
    Map<Integer, Map<Word, Double>> calcKeyWords(Map<Integer, List<Word>> corpus);
}
