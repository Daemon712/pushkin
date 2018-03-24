package io.blockchain.pushkin.bot.handler;

import com.pengrad.telegrambot.model.Message;
import io.blockchain.pushkin.dto.UserRating;
import io.blockchain.pushkin.service.api.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component("chatRatingReportHandler")
public class ChatRatingReportHandler implements Handler {
    private static final String HEADER = "Рейтинг:\n";
    private static final String EMPTY_RESPONSE = "Нет данных для анализа :(";
    private static final String LINE_PATTERN = "%d. [XXX](tg://user?id=%d) %.2f";

    private ReportService reportService;

    @Autowired
    public ChatRatingReportHandler(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public String handle(Message message) {
        List<UserRating> userRatings = reportService.buildChatRatingReport(message.chat().id());
        int size = userRatings.size();
        if (size == 0){
          return EMPTY_RESPONSE;
        } else if (size <= 7) {
            return HEADER + formatLines(IntStream.range(0, size), userRatings);
        } else {
            return HEADER +
                    formatLines(IntStream.range(0, 3), userRatings)
                    + "\n...\n" +
                    formatLines(IntStream.range(size - 3, size), userRatings);
        }
    }

    private String formatLines(IntStream intStream, List<UserRating> userRatings) {
        return intStream
                .mapToObj(i -> String.format(LINE_PATTERN, i + 1, userRatings.get(i).getUserId(), userRatings.get(i).getRating()))
                .collect(Collectors.joining("\n"));

    }
}
