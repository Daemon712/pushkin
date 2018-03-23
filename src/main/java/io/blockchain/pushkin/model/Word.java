package io.blockchain.pushkin.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
public class Word implements Serializable {
    @Column(length = 4096)
    private String word;

    @Column
    @Enumerated(EnumType.STRING)
    private SpeechPart speechPart;

    public Word() {
    }

    public Word(String word, SpeechPart speechPart) {
        this.word = word;
        this.speechPart = speechPart;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public SpeechPart getSpeechPart() {
        return speechPart;
    }

    public void setSpeechPart(SpeechPart speechPart) {
        this.speechPart = speechPart;
    }
}
