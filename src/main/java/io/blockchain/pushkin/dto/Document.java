package io.blockchain.pushkin.dto;

import io.blockchain.pushkin.model.Word;

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
    public String toString() {
        return "Document{" +
                "userId=" + userId +
                ", word=" + word +
                '}';
    }
}
