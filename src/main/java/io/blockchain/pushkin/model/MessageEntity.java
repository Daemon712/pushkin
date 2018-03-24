package io.blockchain.pushkin.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Table
@Entity
public class MessageEntity {
    @EmbeddedId
    private MessagePK messagePK;

    @Column
    private Integer userId;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
        this.literacy= literacy;
    }
}
