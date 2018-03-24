package io.blockchain.pushkin.service.api;

import io.blockchain.pushkin.model.Word;

public interface FrequencyAnalysisService {
    Word adviceNearestUnusedWord(Integer userId);
}
