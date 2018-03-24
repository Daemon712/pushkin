package io.blockchain.pushkin;

import io.blockchain.pushkin.model.MessageEntity;
import io.blockchain.pushkin.model.MessagePK;
import io.blockchain.pushkin.model.TgUser;
import io.blockchain.pushkin.repo.MessageEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ChatLoader {
    private final MessageEntityRepository messageEntityRepository;

    @Autowired
    public ChatLoader(MessageEntityRepository messageEntityRepository) {
        this.messageEntityRepository = messageEntityRepository;
    }

    @PostConstruct
    public void loadMessages() {
        List<MessageEntity> messageEntities = new ArrayList<>();
        int messId = 1;
        int CHAT_ID = 123123;//TODO Заменить на ID реального чата
        try (BufferedReader br = new BufferedReader(new FileReader(new File(Paths.get("src", "main", "resources", "messages.txt").toString())))) {
            String line;
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            Pattern pattern = Pattern.compile("([0-9][0-9].[0-9][0-9].[0-9][0-9][0-9][0-9] [0-9][0-9]:[0-9][0-9]:[0-9][0-9]), [\\d\\w _]*\\[[\\d\\w _]*\\|(\\w*)\\]: ([^\\>]*)");
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (!line.contains("DELETED:") && matcher.find()) {
                    MessageEntity me = new MessageEntity();
                    me.setDate(formatter.parse(matcher.group(1)));
                    me.setUser(new TgUser(Integer.parseInt(matcher.group(2)), ""));
                    me.setMessagePK(new MessagePK(CHAT_ID,messId++));
                    me.setText(matcher.group(3));
                    messageEntities.add(me);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        messageEntityRepository.saveAll(messageEntities);
    }
}
