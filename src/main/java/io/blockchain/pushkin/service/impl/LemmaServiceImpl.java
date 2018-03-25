package io.blockchain.pushkin.service.impl;

import io.blockchain.pushkin.model.SpeechPart;
import io.blockchain.pushkin.model.Word;
import io.blockchain.pushkin.service.api.LemmaService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.stachek66.nlp.mystem.holding.Factory;
import ru.stachek66.nlp.mystem.holding.MyStem;
import ru.stachek66.nlp.mystem.holding.MyStemApplicationException;
import ru.stachek66.nlp.mystem.holding.Request;
import ru.stachek66.nlp.mystem.model.Info;
import scala.Option;
import scala.collection.JavaConversions;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LemmaServiceImpl implements LemmaService {
    private final MyStem myStemAnalyzer =
            new Factory("-igd --eng-gr --format json --weight")
                    .newMyStem("3.0", Option.empty()).get();

    @Override
    public List<Word> getLemmas(String text) {
        try {
            Collection<Info> report = JavaConversions.asJavaCollection(
                    myStemAnalyzer
                            .analyze(Request.apply(text))
                            .info()
                            .toIterable());
            return report.stream()
                    .filter(info -> info.lex().isDefined())
                    .map(info -> new Word(info.lex().get(), parseSpeechPart(info.rawResponse())))
                    .collect(Collectors.toList());
        } catch (MyStemApplicationException e) {
            throw new RuntimeException(e);
        }
    }

    private SpeechPart parseSpeechPart(String myStemResponse) {
        JSONObject responseObj = new JSONObject(myStemResponse);
        JSONArray analysis = responseObj.getJSONArray("analysis");
        if (analysis.length() == 0) return null;
        String grammar = analysis.getJSONObject(0).getString("gr");
        String speechPart = grammar.split("\\W")[0];
        switch (speechPart) {
            case "A":
                return SpeechPart.adjective;
            case "ADV":
                return SpeechPart.adverb;
            case "ADVPRO":
                return SpeechPart.pronominal_adverb;
            case "ANUM":
                return SpeechPart.numeral_adjective;
            case "APRO":
                return SpeechPart.pronoun_adjective;
            case "COM":
                return SpeechPart.composite_part;
            case "CONJ":
                return SpeechPart.conjunction;
            case "INTJ":
                return SpeechPart.interjection;
            case "NUM":
                return SpeechPart.numeral;
            case "PART":
                return SpeechPart.particle;
            case "PR":
                return SpeechPart.pretext;
            case "S":
                return SpeechPart.noun;
            case "SPRO":
                return SpeechPart.pronoun_noun;
            case "V":
                return SpeechPart.verb;
            default:
                return null;
        }
    }
}
