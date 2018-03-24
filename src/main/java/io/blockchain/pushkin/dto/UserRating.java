package io.blockchain.pushkin.dto;

import io.blockchain.pushkin.model.TgUser;

public class UserRating {
    private TgUser user;
    private Double rating;

    public UserRating() {
    }

    public UserRating(TgUser user, Double rating) {
        this.user = user;
        this.rating = rating;
    }

    public TgUser getUser() {
        return user;
    }

    public void setUser(TgUser userId) {
        this.user = userId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
