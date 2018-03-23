package io.blockchain.pushkin.bot.handler;

import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Component;

@Component("startHandler")
public class StartHandler implements Handler {
    @Override
    public String handle(Message message) {
        return "Привет, друг!";
    }
}
