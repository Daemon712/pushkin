package io.blockchain.pushkin.service.api;

import io.blockchain.pushkin.dto.Report;
import io.blockchain.pushkin.dto.UserRating;

import java.util.List;

public interface ReportService {
    Report buildUserReport(Long chatId, Integer userId);

    List<UserRating> buildChatRatingReport(Long chatId);

    List<UserRating> buildChatLiteracyReport(Long chatId);

    Report buildChatReport(Long chatId);
}
