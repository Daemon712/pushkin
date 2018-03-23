package io.blockchain.pushkin.service.api;

import com.pengrad.telegrambot.model.Message;

public interface CollectorService {
    void handleMessage(Message message);
}
