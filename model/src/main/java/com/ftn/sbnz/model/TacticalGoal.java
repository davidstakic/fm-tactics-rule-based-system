package com.ftn.sbnz.model;

public class TacticalGoal {
    private String goal;
    private String requirement;
    private String level;
    private String explanation;

    public TacticalGoal() {
    }

    public TacticalGoal(String goal, String requirement, String level, String explanation) {
        this.goal = goal;
        this.requirement = requirement;
        this.level = level;
        this.explanation = explanation;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Override
    public String toString() {
        return "TacticalGoal{" +
                "goal='" + goal + '\'' +
                ", requirement='" + requirement + '\'' +
                ", level='" + level + '\'' +
                ", explanation='" + explanation + '\'' +
                '}';
    }
}

