package io.blockchain.pushkin;

import io.blockchain.pushkin.service.api.SynonymService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SynonymTest {

    @Autowired
    private SynonymService synonymService;

    @Test
    public void test() {
        List<String> synonyms = synonymService.getSynonyms("тупой");
        System.out.println(synonyms);
    }

    @Test
    public void test2() {
        List<String> synonyms = synonymService.getSynonyms("красивый");
        System.out.println(synonyms);
    }

    @Test
    public void test3() {
        List<String> synonyms = synonymService.getSynonyms("хватит");
        //[]
        System.out.println(synonyms);
    }
}
