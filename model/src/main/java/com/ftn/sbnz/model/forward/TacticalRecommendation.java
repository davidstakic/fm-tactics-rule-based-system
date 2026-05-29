package com.ftn.sbnz.model.forward;

import java.util.ArrayList;
import java.util.List;

public class TacticalRecommendation {
    private BasicTacticalSettings basicSettings;
    private KeyTeamInstructions teamInstructions;
    private List<TacticalExplanationStep> explanationSteps;
    private List<TacticalAdvantage> advantages;
    private List<TacticalRisk> risks;

    public TacticalRecommendation() {
        this.explanationSteps = new ArrayList<>();
        this.advantages = new ArrayList<>();
        this.risks = new ArrayList<>();
    }

    public TacticalRecommendation(BasicTacticalSettings basicSettings, KeyTeamInstructions teamInstructions,
            List<TacticalExplanationStep> explanationSteps, List<TacticalAdvantage> advantages,
            List<TacticalRisk> risks) {
        this.basicSettings = basicSettings;
        this.teamInstructions = teamInstructions;
        this.explanationSteps = explanationSteps != null ? explanationSteps : new ArrayList<>();
        this.advantages = advantages != null ? advantages : new ArrayList<>();
        this.risks = risks != null ? risks : new ArrayList<>();
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

    public List<TacticalExplanationStep> getExplanationSteps() {
        return explanationSteps;
    }

    public void setExplanationSteps(List<TacticalExplanationStep> explanationSteps) {
        this.explanationSteps = explanationSteps != null ? explanationSteps : new ArrayList<>();
    }

    public List<TacticalAdvantage> getAdvantages() {
        return advantages;
    }

    public void setAdvantages(List<TacticalAdvantage> advantages) {
        this.advantages = advantages != null ? advantages : new ArrayList<>();
    }

    public List<TacticalRisk> getRisks() {
        return risks;
    }

    public void setRisks(List<TacticalRisk> risks) {
        this.risks = risks != null ? risks : new ArrayList<>();
    }

    public void addExplanationStep(TacticalExplanationStep step) {
        this.explanationSteps.add(step);
    }

    public void addAdvantage(TacticalAdvantage advantage) {
        this.advantages.add(advantage);
    }

    public void addRisk(TacticalRisk risk) {
        this.risks.add(risk);
    }

    @Override
    public String toString() {
        return "TacticalRecommendation{" +
                "basicSettings=" + basicSettings +
                ", teamInstructions=" + teamInstructions +
                ", explanationSteps=" + explanationSteps +
                ", advantages=" + advantages +
                ", risks=" + risks +
                '}';
    }
}
