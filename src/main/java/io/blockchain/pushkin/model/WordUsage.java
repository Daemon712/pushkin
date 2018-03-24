package io.blockchain.pushkin.model;

import javax.persistence.*;

@Entity
@Table
public class WordUsage {
    @EmbeddedId
    private WordUsagePK wordUsagePK;

    @Embedded
    private Word word;

    @MapsId("messagePK")
    @JoinColumns({
            @JoinColumn(name = "chatId"),
            @JoinColumn(name = "messageId")
    })
    @ManyToOne
    private MessageEntity message;

    public WordUsage() {
    }

    public WordUsage(MessagePK messagePK, Integer index, Word word) {
        this.wordUsagePK = new WordUsagePK(messagePK, index);
        this.word = word;
    }

    public WordUsage(WordUsagePK wordUsagePK, Word word) {
        this.wordUsagePK = wordUsagePK;
        this.word = word;
    }

    public WordUsagePK getWordUsagePK() {
        return wordUsagePK;
    }

    public void setWordUsagePK(WordUsagePK wordUsagePK) {
        this.wordUsagePK = wordUsagePK;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public MessageEntity getMessage() {
        return message;
    }

    public void setMessage(MessageEntity message) {
        this.message = message;
    }
}
