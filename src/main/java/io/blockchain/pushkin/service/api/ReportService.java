package io.blockchain.pushkin.service.api;

import io.blockchain.pushkin.dto.Report;
import io.blockchain.pushkin.dto.UserRating;

import java.util.List;

public interface ReportService {
    Report buildUserReport(Integer userId);

    List<UserRating> buildChatRatingReport(Long chatId);

    Report buildChatReport(Long chatId);
}
