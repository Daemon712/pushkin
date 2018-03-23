package io.blockchain.pushkin.service.api;

import java.util.List;

public interface LemmatisationService {
    //TODO from Object to Lemm class
    List<Object> getLemmas(String messageText);
}
