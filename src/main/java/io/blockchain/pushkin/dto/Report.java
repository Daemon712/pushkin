package io.blockchain.pushkin.dto;

import java.time.LocalDateTime;

public class Report {
    private String content;
    private LocalDateTime reportDateTime;
    private Long uniqueWords;
    private Long totalWords;
    private Double rating;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getReportDateTime() {
        return reportDateTime;
    }

    public void setReportDateTime(LocalDateTime reportDateTime) {
        this.reportDateTime = reportDateTime;
    }

    public Long getUniqueWords() {
        return uniqueWords;
    }

    public void setUniqueWords(Long uniqueWords) {
        this.uniqueWords = uniqueWords;
    }

    public Long getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(Long totalWords) {
        this.totalWords = totalWords;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Report{" +
                "content='" + content + '\'' +
                ", reportDateTime=" + reportDateTime +
                ", uniqueWords=" + uniqueWords +
                ", totalWords=" + totalWords +
                ", rating=" + rating +
                '}';
    }
}
