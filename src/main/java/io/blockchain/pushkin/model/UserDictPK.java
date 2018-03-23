package io.blockchain.pushkin.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@Embeddable
public class UserDictPK implements Serializable {
    @Embedded
    private MessagePK messagePK;

    @Column
    private Integer index;

    public UserDictPK() {
    }

    public UserDictPK(MessagePK messagePK, Integer index) {
        this.messagePK = messagePK;
        this.index = index;
    }

    public MessagePK getMessagePK() {
        return messagePK;
    }

    public void setMessagePK(MessagePK messagePK) {
        this.messagePK = messagePK;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
