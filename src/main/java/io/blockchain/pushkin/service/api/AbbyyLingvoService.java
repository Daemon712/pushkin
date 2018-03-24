package io.blockchain.pushkin.service.api;

public interface AbbyyLingvoService {
    String auth();

    String getArticle(String heading, String dict, Integer srcLang, Integer targetLang);
}
