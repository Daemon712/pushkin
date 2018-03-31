package io.blockchain.pushkin.service.impl;

import io.blockchain.pushkin.service.api.SpellCheckerService;
import org.languagetool.JLanguageTool;
import org.languagetool.rules.RuleMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SpellCheckerServiceImpl implements SpellCheckerService {
    private final JLanguageTool languageTool;

    @Autowired
    public SpellCheckerServiceImpl(JLanguageTool languageTool) {
        this.languageTool = languageTool;
    }

    @Override
    public Double checkMessage(String message, int amountOfWords) {
        if (amountOfWords == 0) {
            return null;
        } else {
            try {
                List<RuleMatch> matches = languageTool.check(message);
                return (1 - (matches.size() / amountOfWords)) * 100.0;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
