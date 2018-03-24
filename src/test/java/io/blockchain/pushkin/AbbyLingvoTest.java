package io.blockchain.pushkin;

import io.blockchain.pushkin.service.api.AbbyyLingvoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AbbyLingvoTest {
    private AbbyyLingvoService lingvoService;

    @Autowired
    public void setLingvoTest(AbbyyLingvoService lingvoService) {
        this.lingvoService = lingvoService;
    }

    @Test
    public void testArticle1() {
        String article = lingvoService.getArticle("демократия", "Explanatory (Ru-Ru)", 1049, 1049);
        System.out.println(article);
        Assert.assertNotNull(article);
    }

    @Test
    public void testArticle2() {
        String article = lingvoService.getArticle("монархия", "Explanatory (Ru-Ru)", 1049, 1049);
        System.out.println(article);
        Assert.assertNotNull(article);
    }
}
