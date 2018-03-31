package io.blockchain.pushkin.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@Embeddable
public class WordUsagePK implements Serializable {
    @Embedded
    private MessagePK messagePK;

    @Column
    private Integer index;

    public WordUsagePK() {
    }

    public WordUsagePK(MessagePK messagePK, Integer index) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordUsagePK that = (WordUsagePK) o;

        if (messagePK != null ? !messagePK.equals(that.messagePK) : that.messagePK != null) return false;
        return index != null ? index.equals(that.index) : that.index == null;
    }

    @Override
    public int hashCode() {
        int result = messagePK != null ? messagePK.hashCode() : 0;
        result = 31 * result + (index != null ? index.hashCode() : 0);
        return result;
    }
}
