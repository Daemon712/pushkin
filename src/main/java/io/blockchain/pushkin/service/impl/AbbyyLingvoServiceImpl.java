package io.blockchain.pushkin.service.impl;

import com.jayway.jsonpath.JsonPath;
import io.blockchain.pushkin.service.api.AbbyyLingvoService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbbyyLingvoServiceImpl implements AbbyyLingvoService {
    private static final String url = "https://developers.lingvolive.com/";
    private String basicToken;
    private String bearerToken;
    private OkHttpClient httpClient;

    @Override
    public String auth() {
        Request request = new Request.Builder()
                .url(url + "api/v1.1/authenticate")
                .addHeader("Authorization", "Basic " + basicToken)
                .post(RequestBody.create(null, ""))
                .build();
        try {
            ResponseBody responseBody = httpClient.newCall(request).execute().body();
            if (responseBody != null) {
                return responseBody.string();
            } else {
                throw new RuntimeException("can't auth ABBYY Lingvo");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getArticle(String heading, String dict, Integer srcLang, Integer dstLang) {
        synchronized (this) {
            if (bearerToken == null) bearerToken = auth();
        }

        HttpUrl.Builder httpBuilder = HttpUrl.parse(url + "api/v1/Article").newBuilder();
        httpBuilder.addQueryParameter("heading", heading);
        httpBuilder.addQueryParameter("dict", dict);
        httpBuilder.addQueryParameter("srcLang", String.valueOf(srcLang));
        httpBuilder.addQueryParameter("dstLang", String.valueOf(dstLang));
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .addHeader("Authorization", "Bearer " + bearerToken)
                .get()
                .build();

        try {
            Response response = httpClient.newCall(request).execute();
            switch (response.code()) {
                case 200:
                    List<String> textNodes = JsonPath.read(response.body().string(), "$.Body..[?(@.Node=='Text')].Text");
                    return textNodes.stream().collect(Collectors.joining("\n"));
                case 401:
                    bearerToken = null;
                    return getArticle(heading, dict, srcLang, dstLang);
                default:
                    return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Value("${LingvoToken}")
    public void setBasicToken(String basicToken) {
        this.basicToken = basicToken;
    }

    @Autowired
    public void setHttpClient(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
