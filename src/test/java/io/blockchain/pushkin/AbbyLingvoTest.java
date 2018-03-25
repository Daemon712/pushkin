package io.blockchain.pushkin;

import io.blockchain.pushkin.service.api.AbbyyLingvoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.blockchain.pushkin.service.api.AbbyyLingvoService.DICT_EXPLANATORY_RU_RU;
import static io.blockchain.pushkin.service.api.AbbyyLingvoService.LANG_CODE_RUS;

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
        String article = lingvoService.getArticle("демократия", DICT_EXPLANATORY_RU_RU, LANG_CODE_RUS, LANG_CODE_RUS);
        System.out.println(article);
        Assert.assertNotNull(article);
    }

    @Test
    public void testArticle2() {
        String article = lingvoService.getArticle("монархия", DICT_EXPLANATORY_RU_RU, LANG_CODE_RUS, LANG_CODE_RUS);
        System.out.println(article);
        Assert.assertNotNull(article);
    }
}
