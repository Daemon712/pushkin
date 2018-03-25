package io.blockchain.pushkin.service.api;

import io.blockchain.pushkin.dto.Advice;

public interface AdviceService {
    Advice getAdvice(Integer userId);
}
