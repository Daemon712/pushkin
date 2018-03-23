package io.blockchain.pushkin.bot.handler;

import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Component;

@Component("noneHandler")
public class NoneHandler implements Handler {
    @Override
    public String handle(Message message) {
        return "Wow";
    }
}
