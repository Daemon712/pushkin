package io.blockchain.pushkin.service.impl;

import io.blockchain.pushkin.model.WordSynonym;
import io.blockchain.pushkin.repo.WordSynonymRepository;
import io.blockchain.pushkin.service.api.SynonymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SynonymServiceImpl implements SynonymService {
    private final WordSynonymRepository wordSynonymRepository;

    @Autowired
    public SynonymServiceImpl(WordSynonymRepository wordSynonymRepository) {
        this.wordSynonymRepository = wordSynonymRepository;
    }

    @Override
    public List<String> getSynonyms(String word) {
        Optional<WordSynonym> byWord = wordSynonymRepository.findByWord(word);
        return byWord.map(wordSynonym -> Arrays.asList(wordSynonym.getSynonyms())).orElseGet(Collections::emptyList);
    }
}
