package io.blockchain.pushkin.bot.handler;

import com.pengrad.telegrambot.model.Message;

public interface Handler {
    String handle(Message message);
}
