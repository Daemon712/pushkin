package io.blockchain.pushkin.model;

import javax.persistence.*;
import java.util.Date;

@Table
@Entity
public class MessageEntity {
    @EmbeddedId
    private MessagePK messagePK;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private TgUser user;

    @Column
    private Date date;

    @Column(length = 4096)
    private String text;

    @Column
    private Double literacy;

    public MessagePK getMessagePK() {
        return messagePK;
    }

    public void setMessagePK(MessagePK messagePK) {
        this.messagePK = messagePK;
    }

    public TgUser getUser() {
        return user;
    }

    public void setUser(TgUser user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getLiteracy() {
        return literacy;
    }

    public void setLiteracy(Double literacy) {
        this.literacy = literacy;
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "messagePK=" + messagePK +
                ", user=" + user +
                ", date=" + date +
                ", text='" + text + '\'' +
                ", literacy=" + literacy +
                '}';
    }
}
