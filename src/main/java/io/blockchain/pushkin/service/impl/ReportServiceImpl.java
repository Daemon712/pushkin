package io.blockchain.pushkin.service.impl;

import io.blockchain.pushkin.dto.Report;
import io.blockchain.pushkin.service.api.ReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReportServiceImpl implements ReportService {
    @Override
    public Report buildReport(Integer userId) {
        //TODO build report
        Report report = new Report();
        report.setReportDateTime(LocalDateTime.now());
        report.setContent("РЕПОРТ для " + userId);
        return report;
    }
}
