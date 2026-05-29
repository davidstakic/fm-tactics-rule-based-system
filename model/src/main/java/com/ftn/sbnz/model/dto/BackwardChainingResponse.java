package com.ftn.sbnz.model.dto;

import java.util.List;

public class BackwardChainingResponse {
    private String targetGoalTitle;
    private String explanation;
    private List<BackwardRequirement> requirements;

    public BackwardChainingResponse() {
    }

    public BackwardChainingResponse(String targetGoalTitle, String explanation,
            List<BackwardRequirement> requirements) {
        this.targetGoalTitle = targetGoalTitle;
        this.explanation = explanation;
        this.requirements = requirements;
    }

    public String getTargetGoalTitle() {
        return targetGoalTitle;
    }

    public void setTargetGoalTitle(String targetGoalTitle) {
        this.targetGoalTitle = targetGoalTitle;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public List<BackwardRequirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<BackwardRequirement> requirements) {
        this.requirements = requirements;
    }
}
