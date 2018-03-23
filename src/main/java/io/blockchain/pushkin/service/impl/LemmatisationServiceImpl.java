package io.blockchain.pushkin.service.impl;

import io.blockchain.pushkin.service.api.LemmatisationService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LemmatisationServiceImpl implements LemmatisationService {
    @Override
    public List<Object> getLemmas(String messageText) {
        //TODO invoke mystem
        return Collections.emptyList();
    }
}
