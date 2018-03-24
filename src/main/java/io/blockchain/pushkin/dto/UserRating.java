package io.blockchain.pushkin.dto;

public class UserRating {
    private Integer userId;
    private Double rating;

    public UserRating() {
    }

    public UserRating(Integer userId, Double rating) {
        this.userId = userId;
        this.rating = rating;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
