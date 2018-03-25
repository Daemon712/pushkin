package io.blockchain.pushkin;

import io.blockchain.pushkin.model.WordSynonym;
import io.blockchain.pushkin.repo.WordSynonymRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SynonymsLoader {
    private final WordSynonymRepository wordSynonymRepository;

    @Autowired
    public SynonymsLoader(WordSynonymRepository wordSynonymRepository) {
        this.wordSynonymRepository = wordSynonymRepository;
    }

    @PostConstruct
    public void load() {
        if (wordSynonymRepository.count() > 0) {
            return;
        }
        File file = new File(getClass().getClassLoader().getResource("syn.txt").getFile());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            List<WordSynonym> lst = new ArrayList<>(20_000);
            while ((line = reader.readLine()) != null) {
                line = line.toLowerCase();
                int latticeIndex = line.indexOf("#");
                String word = line.substring(0, latticeIndex)
                        .replace("!", "")
                        .replace("?", "").trim();
                String substring = line.substring(latticeIndex + 1, line.length());
                String syns = substring
                        .replace("см.", ",")
                        .replace("|", ",")
                        .replace(".", ",")
                        .replace(";", ",")
                        .replace("!", "")
                        .replace("[", "")
                        .replace("]", "")
                        .replace("<", "")
                        .replace(">", "")
                        .replace("?", "");
                String[] synonyms = Arrays.stream(syns.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .toArray(String[]::new);
                lst.add(new WordSynonym(word, synonyms));
            }
            wordSynonymRepository.saveAll(lst);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
