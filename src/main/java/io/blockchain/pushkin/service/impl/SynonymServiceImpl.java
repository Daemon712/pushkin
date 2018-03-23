package io.blockchain.pushkin.service.impl;

import io.blockchain.pushkin.service.api.SynonymService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SynonymServiceImpl implements SynonymService {
    @Override
    public List<String> getSynonyms(String word) {
        //TODO implement synonyms (abbylingvo, word2vec etc.)
        return Collections.emptyList();
    }
}
