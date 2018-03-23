package io.blockchain.pushkin.service.api;

import io.blockchain.pushkin.model.Word;

import java.util.List;

public interface LemmaService {
    List<Word> getLemmas(String messageText);
}
