package com.ftn.sbnz.model.forward;

import java.util.ArrayList;
import java.util.List;

public class TacticalRecommendation {
    private BasicTacticalSettings basicSettings;
    private KeyTeamInstructions teamInstructions;
    private String stepByStepExplanation;
    private List<String> potentialRisks;

    public TacticalRecommendation() {
        this.potentialRisks = new ArrayList<>();
    }

    public TacticalRecommendation(BasicTacticalSettings basicSettings, KeyTeamInstructions teamInstructions,
            String stepByStepExplanation, List<String> potentialRisks) {
        this.basicSettings = basicSettings;
        this.teamInstructions = teamInstructions;
        this.stepByStepExplanation = stepByStepExplanation;
        this.potentialRisks = potentialRisks != null ? potentialRisks : new ArrayList<>();
    }

    public BasicTacticalSettings getBasicSettings() {
        return basicSettings;
    }

    public void setBasicSettings(BasicTacticalSettings basicSettings) {
        this.basicSettings = basicSettings;
    }

    public KeyTeamInstructions getTeamInstructions() {
        return teamInstructions;
    }

    public void setTeamInstructions(KeyTeamInstructions teamInstructions) {
        this.teamInstructions = teamInstructions;
    }

    public String getStepByStepExplanation() {
        return stepByStepExplanation;
    }

    public void setStepByStepExplanation(String stepByStepExplanation) {
        this.stepByStepExplanation = stepByStepExplanation;
    }

    public List<String> getPotentialRisks() {
        return potentialRisks;
    }

    public void setPotentialRisks(List<String> potentialRisks) {
        this.potentialRisks = potentialRisks;
    }

    public void addPotentialRisk(String risk) {
        this.potentialRisks.add(risk);
    }

    @Override
    public String toString() {
        return "TacticalRecommendation{" +
                "basicSettings=" + basicSettings +
                ", teamInstructions=" + teamInstructions +
                ", stepByStepExplanation='" + stepByStepExplanation + '\'' +
                ", potentialRisks=" + potentialRisks +
                '}';
    }
}
