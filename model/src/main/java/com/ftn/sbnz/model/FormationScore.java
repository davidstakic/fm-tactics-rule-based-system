package com.ftn.sbnz.model;

import com.ftn.sbnz.model.BasicTacticalSettings.Formation;
import java.util.HashMap;
import java.util.Map;

public class FormationScore {
    private Formation formation;
    private Integer totalScore;
    private Map<String, Integer> scoreBreakdown;

    public FormationScore() {
        this.totalScore = 0;
        this.scoreBreakdown = new HashMap<>();
    }

    public FormationScore(Formation formation) {
        this.formation = formation;
        this.totalScore = 0;
        this.scoreBreakdown = new HashMap<>();
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Map<String, Integer> getScoreBreakdown() {
        return scoreBreakdown;
    }

    public void setScoreBreakdown(Map<String, Integer> scoreBreakdown) {
        this.scoreBreakdown = scoreBreakdown;
    }

    public void addScore(String criterion, Integer points) {
        this.scoreBreakdown.put(criterion, points);
        this.totalScore += points;
    }

    public void subtractScore(String criterion, Integer points) {
        this.scoreBreakdown.put(criterion, -points);
        this.totalScore -= points;
    }

    @Override
    public String toString() {
        return "FormationScore{" +
                "formation=" + formation +
                ", totalScore=" + totalScore +
                ", scoreBreakdown=" + scoreBreakdown +
                '}';
    }
}
