package com.ftn.sbnz.model.forward;

import java.util.ArrayList;
import java.util.List;

public class TacticalExplanationStep {
    private String decision;
    private String selectedValue;
    private String explanation;
    private List<TacticalAdvantage> advantages;
    private List<TacticalRisk> risks;

    public TacticalExplanationStep() {
        this.advantages = new ArrayList<>();
        this.risks = new ArrayList<>();
    }

    public TacticalExplanationStep(String decision, String selectedValue, String explanation) {
        this();
        this.decision = decision;
        this.selectedValue = selectedValue;
        this.explanation = explanation;
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

    public void addAdvantage(String title, String description) {
        this.advantages.add(new TacticalAdvantage(title, description));
    }

    public void addRisk(String title, String description) {
        this.risks.add(new TacticalRisk(title, description));
    }

    public void addTradeoffsTo(TacticalRecommendation recommendation) {
        for (TacticalAdvantage advantage : advantages) {
            recommendation.addAdvantage(advantage);
        }
        for (TacticalRisk risk : risks) {
            recommendation.addRisk(risk);
        }
    }

    @Override
    public String toString() {
        return "TacticalExplanationStep{" +
                "decision='" + decision + '\'' +
                ", selectedValue='" + selectedValue + '\'' +
                ", explanation='" + explanation + '\'' +
                ", advantages=" + advantages +
                ", risks=" + risks +
                '}';
    }
}
