package com.ftn.sbnz.model.forward;

import java.util.ArrayList;
import java.util.List;

public class TacticalExplanationStep {
    private String level;
    private String decision;
    private String selectedValue;
    private String explanation;
    private List<TacticalAdvantage> advantages;
    private List<TacticalRisk> risks;

    public TacticalExplanationStep() {
        this.advantages = new ArrayList<>();
        this.risks = new ArrayList<>();
    }

    public TacticalExplanationStep(String level, String decision, String selectedValue, String explanation) {
        this();
        this.level = level;
        this.decision = decision;
        this.selectedValue = selectedValue;
        this.explanation = explanation;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
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

    public void addAdvantage(String title, String description) {
        this.advantages.add(new TacticalAdvantage(title, description));
    }

    public void addRisk(String title, String description) {
        this.risks.add(new TacticalRisk(title, description));
    }

    @Override
    public String toString() {
        return "TacticalExplanationStep{" +
                "level='" + level + '\'' +
                ", decision='" + decision + '\'' +
                ", selectedValue='" + selectedValue + '\'' +
                ", explanation='" + explanation + '\'' +
                ", advantages=" + advantages +
                ", risks=" + risks +
                '}';
    }
}
