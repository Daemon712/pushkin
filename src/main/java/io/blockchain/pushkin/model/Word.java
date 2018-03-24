package io.blockchain.pushkin.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word) &&
                speechPart == word1.speechPart;
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, speechPart);
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", speechPart=" + speechPart +
                '}';
    }
}
