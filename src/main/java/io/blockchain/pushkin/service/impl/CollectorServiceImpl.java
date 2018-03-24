package io.blockchain.pushkin.service.impl;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import io.blockchain.pushkin.model.*;
import io.blockchain.pushkin.repo.MessageEntityRepository;
import io.blockchain.pushkin.repo.WordUsageRepository;
import io.blockchain.pushkin.service.api.CollectorService;
import io.blockchain.pushkin.service.api.LemmaService;
import io.blockchain.pushkin.service.api.SpellCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CollectorServiceImpl implements CollectorService {
    private MessageEntityRepository messageEntityRepository;
    private WordUsageRepository wordUsageRepository;
    private LemmaService lemmaService;
    private SpellCheckerService spellCheckerService;

    @Async
    @Override
    public void handleMessage(Message message) {
        if (message.text() == null) {
            return;
        }
        MessageEntity messageEntity = new MessageEntity();
        MessagePK messagePK = new MessagePK(message.chat().id(), message.messageId());
        messageEntity.setMessagePK(messagePK);

        User user = message.from();
        String userName = user.firstName() == null ? user.lastName()
                : user.lastName() == null ? user.firstName()
                : user.firstName() + " " + user.lastName();
        messageEntity.setUser(new TgUser(user.id(), userName));
        messageEntity.setText(message.text());
        messageEntity.setDate(new Date(1000L * message.date()));

        List<Word> lemmas = lemmaService.getLemmas(message.text());

        // Spell checking
        Double literacy = spellCheckerService.checkMessage(messageEntity.getText(), lemmas.size());
        if (literacy != null) {
            messageEntity.setLiteracy(literacy);
        }

        messageEntityRepository.save(messageEntity);

        List<WordUsage> wordUsageList = IntStream.range(0, lemmas.size())
                .mapToObj(i -> new WordUsage(new WordUsagePK(messagePK, i), lemmas.get(i)))
                .peek(wordUsage -> wordUsage.setMessage(messageEntity))
                .collect(Collectors.toList());

        wordUsageRepository.saveAll(wordUsageList);
    }

    @Autowired
    public void setMessageEntityRepository(MessageEntityRepository messageEntityRepository) {
        this.messageEntityRepository = messageEntityRepository;
    }

    @Autowired
    public void setWordUsageRepository(WordUsageRepository wordUsageRepository) {
        this.wordUsageRepository = wordUsageRepository;
    }

    @Autowired
    public void setLemmaService(LemmaService lemmaService) {
        this.lemmaService = lemmaService;
    }

    @Autowired
    public void setSpellCheckerService(SpellCheckerService spellCheckerService) {
        this.spellCheckerService = spellCheckerService;
    }
}
