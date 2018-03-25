package io.blockchain.pushkin.bot.handler;

import com.pengrad.telegrambot.model.Message;
import io.blockchain.pushkin.dto.Advice;
import io.blockchain.pushkin.service.api.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("adviceHandler")
public class AdviceHandler implements Handler {
    private final AdviceService adviceService;

    @Autowired
    public AdviceHandler(AdviceService adviceService) {
        this.adviceService = adviceService;
    }

    @Override
    public String handle(Message message) {
        StringBuilder sb = new StringBuilder();
        Advice advice = adviceService.getAdvice(message.from().id());
        advice.getRecommendations().forEach(r -> sb.append(r).append("\n"));
        return sb.toString();
    }
}
