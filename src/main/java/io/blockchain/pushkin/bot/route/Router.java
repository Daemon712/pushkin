package io.blockchain.pushkin.bot.route;

import com.pengrad.telegrambot.model.Message;
import io.blockchain.pushkin.bot.handler.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Router {
    private final Handler onHandler;
    private final Handler noneHandler;
    private final Handler startHandler;
    private final Handler reportHandler;

    @Autowired
    public Router(@Qualifier("onHandler") Handler onHandler,
                  @Qualifier("noneHandler") Handler noneHandler,
                  @Qualifier("startHandler") Handler startHandler,
                  @Qualifier("reportHandler") Handler reportHandler) {
        this.onHandler = onHandler;
        this.noneHandler = noneHandler;
        this.startHandler = startHandler;
        this.reportHandler = reportHandler;
    }

    /**
     * Routes commands to handlers
     *
     * @param message - user's message
     * @return handler
     */
    public Handler route(Message message) {
        if (message.text() != null) {
            switch (message.text()) {
                case "/start":
                    return startHandler;
                case "/on":
                    return onHandler;
                case "/report":
                    return reportHandler;
                default:
                    return noneHandler;
            }
        }
        return noneHandler;
    }
}
