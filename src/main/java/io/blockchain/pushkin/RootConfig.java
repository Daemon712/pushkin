package io.blockchain.pushkin;

import io.blockchain.pushkin.bot.PushkinBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RootConfig {

    @Value("${botToken}")
    private String botToken;

    @PostConstruct
    public void init() {
        PushkinBot pushkinBot = new PushkinBot();
        pushkinBot.run(botToken);
    }
}
