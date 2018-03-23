package io.blockchain.pushkin.bot.handler;

import com.pengrad.telegrambot.model.Message;
import io.blockchain.pushkin.service.api.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("reportHandler")
public class ReportHandler implements Handler {
    private ReportService reportService;

    @Autowired
    public ReportHandler(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public String handle(Message message) {
        //TODO implement formatting
        return reportService.buildReport(message.from().id()).toString();
    }
}
