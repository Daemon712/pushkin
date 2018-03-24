package io.blockchain.pushkin.service.api;

public interface SpellCheckerService {
    Double checkMessage(String message, int amountOfWords);
}
