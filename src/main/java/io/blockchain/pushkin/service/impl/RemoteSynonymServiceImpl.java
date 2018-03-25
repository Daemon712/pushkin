package io.blockchain.pushkin.service.impl;

import io.blockchain.pushkin.service.api.SynonymService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("remote")
public class RemoteSynonymServiceImpl implements SynonymService {
    private static final String BASE_URL = "https://sinonim.org/s/";

    @Override
    public List<String> getSynonyms(String word) {
        List<String> lst = new ArrayList<>();
        try {
            Document document = Jsoup.connect(BASE_URL + word).get();
            Element a = document.select(".onlywords").first();
            if (a == null) {
                return Collections.emptyList();
            }
            Element element = a.nextElementSibling();
            for (int i = 1; i <= 3; i++) {
                Element tr = element.getElementById("tr" + i);
                String text = tr.children().get(1).text();
                if (text == null) continue;
                int ind = text.indexOf("(");
                if (ind != -1) {
                    text = text.substring(0, ind);
                }
                text = text.trim();
                if (!text.isEmpty()) {
                    lst.add(text);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lst;
    }
}
