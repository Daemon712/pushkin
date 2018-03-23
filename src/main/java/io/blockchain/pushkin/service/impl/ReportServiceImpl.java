package io.blockchain.pushkin.service.impl;

import io.blockchain.pushkin.dto.Report;
import io.blockchain.pushkin.repo.WordUsageRepository;
import io.blockchain.pushkin.service.api.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReportServiceImpl implements ReportService {
    private WordUsageRepository wordUsageRepository;

    @Override
    public Report buildUserReport(Integer userId) {
        Report report = new Report();
        report.setReportDateTime(LocalDateTime.now());
        report.setUniqueWords(wordUsageRepository.countByMessageUserId(userId));
        report.setTotalWords(wordUsageRepository.findDistinctWordByMessageUserId(userId).size());
        return report;
    }

    @Autowired
    public void setWordUsageRepository(WordUsageRepository wordUsageRepository) {
        this.wordUsageRepository = wordUsageRepository;
    }
}
