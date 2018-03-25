package io.blockchain.pushkin;

import io.blockchain.pushkin.model.*;
import io.blockchain.pushkin.repo.MessageEntityRepository;
import io.blockchain.pushkin.repo.WordUsageRepository;
import io.blockchain.pushkin.service.api.LemmaService;
import io.blockchain.pushkin.service.api.SpellCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ChatLoader {
    private final MessageEntityRepository messageEntityRepository;
    private LemmaService lemmaService;
    private SpellCheckerService spellCheckerService;
    private WordUsageRepository wordUsageRepository;

    @Autowired
    public ChatLoader(MessageEntityRepository messageEntityRepository) {
        this.messageEntityRepository = messageEntityRepository;
    }

    @Autowired
    public void setLemmaService(LemmaService lemmaService) {
        this.lemmaService = lemmaService;
    }

    @Autowired
    public void setSpellCheckerService(SpellCheckerService spellCheckerService) {
        this.spellCheckerService = spellCheckerService;
    }

    @Autowired
    public void setWordUsageRepository(WordUsageRepository wordUsageRepository) {
        this.wordUsageRepository = wordUsageRepository;
    }

    // @PostConstruct
    public void loadMessages() {
        loadHMessages();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        final Pattern pattern = Pattern.compile("(-\\d*),(\\d*),(\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d).216,\"?([^\\>]*)\"?,(\\d*)");
        try (BufferedReader br = new BufferedReader(new FileReader(new File(Paths.get("src", "main", "resources", "messages").toString())))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    try {
                        if (matcher.group(4).trim().equals(""))
                            continue;
                        int userId = Integer.parseInt(matcher.group(5));
                        int messageId = Integer.parseInt(matcher.group(2));
                        MessageEntity messageEntity = new MessageEntity();
                        MessagePK messagePK = new MessagePK(-1001038770422l, messageId);
                        messageEntity.setMessagePK(messagePK);


                        messageEntity.setUser(new TgUser(userId, ""));
                        messageEntity.setText(matcher.group(4));
                        messageEntity.setDate(formatter.parse(matcher.group(3)));

                        List<Word> lemmas = lemmaService.getLemmas(matcher.group(4));

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
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadHMessages() {
        int messId = 1;
        int CHAT_ID = 123123;//TODO Заменить на ID реального чата
        try (BufferedReader br = new BufferedReader(new FileReader(new File(Paths.get("src", "main", "resources", "messages.txt").toString())))) {
            String line;
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            Pattern pattern = Pattern.compile("([0-9][0-9].[0-9][0-9].[0-9][0-9][0-9][0-9] [0-9][0-9]:[0-9][0-9]:[0-9][0-9]), ([\\w ]*)[\\d\\w _]*\\[[\\d\\w _]*\\|(\\w*)\\]: ([^\\>]*)");
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    try {
                        int userId = Integer.parseInt(matcher.group(3));
                        MessageEntity messageEntity = new MessageEntity();
                        MessagePK messagePK = new MessagePK(CHAT_ID, messId++);
                        messageEntity.setMessagePK(messagePK);


                        messageEntity.setUser(new TgUser(userId, matcher.group(2)));
                        messageEntity.setText(matcher.group(4));
                        messageEntity.setDate(formatter.parse(matcher.group(1)));

                        List<Word> lemmas = lemmaService.getLemmas(matcher.group(4));

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
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
