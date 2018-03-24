package io.blockchain.pushkin.service.impl;

import io.blockchain.pushkin.service.api.SpellCheckerService;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.language.Russian;
import org.languagetool.rules.CategoryId;
import org.languagetool.rules.RuleMatch;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
            return null;
        } else {
            JLanguageTool langTool = new JLanguageTool(language);
            langTool.disableCategory(new CategoryId("CASING"));
            try {
                List<RuleMatch> matches = langTool.check(message);
                for (RuleMatch match : matches) {
                    System.out.println("Error detected from pos: " + match.getFromPos()
                            + "\nReplacements: " + match.getSuggestedReplacements());
                }
                return (1 - matches.size() / amountOfWords) * 100.0;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
