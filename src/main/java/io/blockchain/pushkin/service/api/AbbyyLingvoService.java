package io.blockchain.pushkin.service.api;

public interface AbbyyLingvoService {
    int LANG_CODE_RUS = 1049;
    String DICT_EXPLANATORY_RU_RU = "Explanatory (Ru-Ru)";

    String auth();

    String getArticle(String heading, String dict, Integer srcLang, Integer targetLang);
}
