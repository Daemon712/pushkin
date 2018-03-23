package io.blockchain.pushkin.service.api;

import java.util.List;

public interface SynonymService {
    List<String> getSynonyms(String word);
}
