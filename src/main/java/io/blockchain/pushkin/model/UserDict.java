package io.blockchain.pushkin.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class UserDict {
    @EmbeddedId
    private MessagePK messagePK;

    @Embedded
    private Word word;

    @Column
    private Date date;

    @Column
    private Integer userId;

    public MessagePK getMessagePK() {
        return messagePK;
    }

    public void setMessagePK(MessagePK messagePK) {
        this.messagePK = messagePK;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
