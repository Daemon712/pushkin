package io.blockchain.pushkin.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import io.blockchain.pushkin.bot.handler.Handler;
import io.blockchain.pushkin.bot.route.Router;
import io.blockchain.pushkin.service.api.CollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PushkinBot {
    private Router router;
    private CollectorService collectorService;

    @Autowired
    public PushkinBot(Router router, CollectorService collectorService) {
        this.router = router;
        this.collectorService = collectorService;
    }

    public void run(String botToken) {
        TelegramBot telegramBot = new TelegramBot(botToken);
        telegramBot.setUpdatesListener(updates -> {
            updates.stream()
                    .map(Update::message)
                    .filter(Objects::nonNull)
                    .peek(collectorService::handleMessage)
                    .forEach(m -> {
                        Handler handler = router.route(m);
                        String response = handler.handle(m);
                        telegramBot.execute(new SendMessage(m.chat().id(), response));
                    });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}

