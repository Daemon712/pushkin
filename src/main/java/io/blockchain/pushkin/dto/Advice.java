package io.blockchain.pushkin.dto;

import java.util.ArrayList;
import java.util.List;

public class Advice {
    private List<String> recommendations;

    public Advice() {
        recommendations = new ArrayList<>();
    }

    public void addRecommendation(String recommendation) {
        recommendations.add(recommendation);
    }

    public boolean isEmpty() {
        return recommendations.isEmpty();
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }
}
