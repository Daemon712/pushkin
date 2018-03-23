package io.blockchain.pushkin.service.api;

import io.blockchain.pushkin.dto.Report;

public interface ReportService {
    Report buildUserReport(Integer userId);
}
