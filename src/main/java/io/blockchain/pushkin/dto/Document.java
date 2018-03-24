package io.blockchain.pushkin.dto;

import io.blockchain.pushkin.model.Word;

import java.util.Objects;

public class Document {
    private int userId;
    private Word word;

    public Document() {
    }

    public Document(int userId, Word word) {
        this.userId = userId;
        this.word = word;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return userId == document.userId &&
                Objects.equals(word, document.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, word);
    }

    @Override
    public String toString() {
        return "Document{" +
                "userId=" + userId +
                ", word=" + word +
                '}';
    }
}
