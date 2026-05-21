package com.ftn.sbnz.model.dto;

import com.ftn.sbnz.model.backward.TacticalGoal;
import java.util.List;

public class BackwardChainingResponse {
    private String targetGoal;
    private List<TacticalGoal> requirements;

    public BackwardChainingResponse() {
    }

    public BackwardChainingResponse(String targetGoal, List<TacticalGoal> requirements) {
        this.targetGoal = targetGoal;
        this.requirements = requirements;
    }

    public String getTargetGoal() {
        return targetGoal;
    }

    public void setTargetGoal(String targetGoal) {
        this.targetGoal = targetGoal;
    }

    public List<TacticalGoal> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<TacticalGoal> requirements) {
        this.requirements = requirements;
    }
}
