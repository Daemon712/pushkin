package io.blockchain.pushkin.bot.handler;

import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Component;

@Component("onHandler")
public class OnHandler implements Handler {
    @Override
    public String handle(Message message) {
        return "Трекинг включен";
    }
}
