package io.blockchain.pushkin.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class PushkinBot {
    public void run(String botToken) {
        TelegramBot telegramBot = new TelegramBot(botToken);
        telegramBot.setUpdatesListener(updates -> {
            updates.stream()
                    .map(Update::message)
                    .forEach(m -> telegramBot.execute(new SendMessage(m.chat().id(), m.text())));
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}

