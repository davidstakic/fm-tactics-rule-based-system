package com.ftn.sbnz.model.dto;

import jakarta.validation.constraints.NotBlank;

public class BackwardChainingRequest {
    @NotBlank(message = "targetGoal is required.")
    private String targetGoal;

    public BackwardChainingRequest() {
    }

    public String getTargetGoal() {
        return targetGoal;
    }

    public void setTargetGoal(String targetGoal) {
        this.targetGoal = targetGoal;
    }

    public String resolveTargetGoal() {
        return targetGoal.trim();
    }
}
