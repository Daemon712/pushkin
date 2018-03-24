package io.blockchain.pushkin.service.impl;

import io.blockchain.pushkin.service.api.SpellCheckerService;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.language.Russian;
import org.languagetool.rules.RuleMatch;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpellCheckerServiceImpl implements SpellCheckerService {

    private static final Language language;

    static {
        language = new Russian();
    }

    @Override
    public Double checkMessage(String message, int amountOfWords) {
        if (amountOfWords == 0) {
            return 0.0;
        } else {
            JLanguageTool langTool = new JLanguageTool(language);

            List<String> strings = new ArrayList<>();
            try {
                List<RuleMatch> matches = langTool.check(message);
                return (double) matches.size() / (double) amountOfWords;
            } catch (IOException e) {
                e.printStackTrace();
                return 0.0;
            }
        }
    }
}
