package io.blockchain.pushkin;

import io.blockchain.pushkin.bot.PushkinBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RootConfig {
    @Value("${botToken}")
    private String botToken;
    private PushkinBot pushkinBot;

    @Autowired
    public RootConfig(PushkinBot pushkinBot) {
        this.pushkinBot = pushkinBot;
    }

    @PostConstruct
    public void init() {
        pushkinBot.run(botToken);
    }
}
