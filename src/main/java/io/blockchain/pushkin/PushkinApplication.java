package io.blockchain.pushkin;

import okhttp3.OkHttpClient;
import org.languagetool.JLanguageTool;
import org.languagetool.language.Russian;
import org.languagetool.rules.CategoryId;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.SimpleThreadScope;

@Configuration
@SpringBootApplication
public class PushkinApplication {
    public static void main(String[] args) {
        SpringApplication.run(PushkinApplication.class, args);
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient().newBuilder().build();
    }

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return beanFactory -> beanFactory.registerScope("thread", new SimpleThreadScope());
    }

    @Bean
    @Scope("thread")
    public JLanguageTool jLanguageTool() {
        JLanguageTool jLanguageTool = new JLanguageTool(language());
        jLanguageTool.disableCategory(new CategoryId("CASING"));
        return jLanguageTool;
    }

    @Bean
    public Russian language() {
        return new Russian();
    }
}
