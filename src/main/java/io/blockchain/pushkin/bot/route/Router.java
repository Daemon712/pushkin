package io.blockchain.pushkin.bot.route;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import io.blockchain.pushkin.bot.handler.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Router {
    @Value("${botName}")
    private String botName;
    private final Handler noneHandler;
    private final Handler startHandler;
    private final Handler userReportHandler;
    private final Handler chatRatingReportHandler;

    @Autowired
    public Router(@Qualifier("noneHandler") Handler noneHandler,
                  @Qualifier("startHandler") Handler startHandler,
                  @Qualifier("userReportHandler") Handler userReportHandler,
                  @Qualifier("chatRatingReportHandler") Handler chatRatingReportHandler) {
        this.noneHandler = noneHandler;
        this.startHandler = startHandler;
        this.userReportHandler = userReportHandler;
        this.chatRatingReportHandler = chatRatingReportHandler;
    }

    /**
     * Routes commands to handlers
     *
     * @param message - user's message
     * @return handler
     */
    public Handler route(Message message) {
        String text = message.text();
        if (text != null) {
            if (Chat.Type.Private.equals(message.chat().type())) {
                return handleText(text);
            } else {
                if (!text.endsWith(botName)) {
                    return noneHandler;
                } else {
                    return handleText(text);
                }
            }
        }
        return noneHandler;
    }

    private Handler handleText(String text) {
        if (text.startsWith("/start")) {
            return startHandler;
        } else if (text.startsWith("/my_stats")) {
            return userReportHandler;
        } else if (text.startsWith("/chat_lex_stats")) {
            return chatRatingReportHandler;
        }
        return noneHandler;
    }
}
