package io.blockchain.pushkin.bot.handler;

import com.pengrad.telegrambot.model.Message;
import io.blockchain.pushkin.service.api.AbbyyLingvoService;
import io.blockchain.pushkin.service.api.LemmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static io.blockchain.pushkin.service.api.AbbyyLingvoService.DICT_EXPLANATORY_RU_RU;
import static io.blockchain.pushkin.service.api.AbbyyLingvoService.LANG_CODE_RUS;

@Component("explainWordHandler")
public class ExplainWordHandler implements Handler {
    private final AbbyyLingvoService abbyyLingvoService;
    private final LemmaService lemmaService;

    @Autowired
    public ExplainWordHandler(AbbyyLingvoService abbyyLingvoService, LemmaService lemmaService) {
        this.abbyyLingvoService = abbyyLingvoService;
        this.lemmaService = lemmaService;
    }

    @Override
    public String handle(Message message) {
        String word = extractRequestedWord(message);
        if (word == null) return "Некорректный запрос. Попробуйте /explain %слово%";

        String lemma = lemmaService.getLemmas(word).get(0).getWord();
        String article = abbyyLingvoService.getArticle(lemma, DICT_EXPLANATORY_RU_RU, LANG_CODE_RUS, LANG_CODE_RUS);

        return article != null
                ? article
                : "Неизвестное слово :(";
    }

    private String extractRequestedWord(Message message) {
        if (message.text() == null) return null;
        String[] split = message.text().split(" ");
        if (split.length < 2) return null;
        return split[1];
    }
}
