package io.blockchain.pushkin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class TgUser {
    @Id
    private Integer userId;

    @Column
    private String fullName;

    public TgUser() {
    }

    public TgUser(Integer userId, String fullName) {
        this.userId = userId;
        this.fullName = fullName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
