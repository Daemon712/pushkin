package io.blockchain.pushkin;

import io.blockchain.pushkin.service.api.SynonymService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SynonymTest {

    @Qualifier("remote")
    @Autowired
    private SynonymService synonymService;

    @Qualifier("local")
    @Autowired
    private SynonymService synonymServiceLocal;

    @Test
    public void test() {
        List<String> lst = Arrays.asList("тупой", "красивый", "бесить", "хватит", "устал", "как бы", "ыкрыкр");
        for (String s : lst) {
            List<String> synonyms = synonymService.getSynonyms(s);
            //not so fast:)
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Синонимы для " + s + " = " + synonyms);
        }
    }

    @Test
    public void testLocal() {
        List<String> lst = Arrays.asList("тупой", "красивый", "бесить", "хватит", "устал", "как бы", "ыкрыкр");
        for (String s : lst) {
            List<String> synonyms = synonymServiceLocal.getSynonyms(s);
            System.out.println("Синонимы для " + s + " = " + synonyms);
        }
    }
}
