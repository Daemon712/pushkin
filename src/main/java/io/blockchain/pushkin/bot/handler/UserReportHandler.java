package io.blockchain.pushkin.bot.handler;

import com.pengrad.telegrambot.model.Message;
import io.blockchain.pushkin.dto.Report;
import io.blockchain.pushkin.service.api.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component("userReportHandler")
public class UserReportHandler implements Handler {
    private static final String PATTERN = "Ваш словарный запас: {1} слов\n" +
            "Ваша оценка: {2} попугаев\n" +
            "Всего проанализировано {0} слов\n" +
            "Грамотность {3}%";

    private ReportService reportService;

    @Autowired
    public UserReportHandler(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public String handle(Message message) {
        Report report = reportService.buildUserReport(message.from().id());
        return MessageFormat.format(PATTERN, report.getTotalWords(), report.getUniqueWords(), report.getRating(), report.getErrorFrequency());
    }
}
