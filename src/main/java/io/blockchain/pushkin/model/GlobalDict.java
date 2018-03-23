package io.blockchain.pushkin.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
public class GlobalDict {
    @EmbeddedId
    private Word word;

    @Column
    private BigDecimal rate;

    @Column
    private Long count;

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
