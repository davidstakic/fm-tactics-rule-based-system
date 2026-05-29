package com.ftn.sbnz.model.forward;

import com.ftn.sbnz.model.enums.Formation;
import com.ftn.sbnz.model.enums.Mentality;
import java.util.ArrayList;
import java.util.List;

public class Level2Facts {
    private Formation selectedFormation;
    private Mentality selectedMentality;
    private Integer formationScore;
    private List<TacticalExplanationStep> explanationSteps;

    public Level2Facts() {
        this.explanationSteps = new ArrayList<>();
    }

    public Level2Facts(Formation selectedFormation,
            Mentality selectedMentality,
            Integer formationScore) {
        this();
        this.selectedFormation = selectedFormation;
        this.selectedMentality = selectedMentality;
        this.formationScore = formationScore;
    }

    public Formation getSelectedFormation() {
        return selectedFormation;
    }

    public void setSelectedFormation(Formation selectedFormation) {
        this.selectedFormation = selectedFormation;
    }

    public Mentality getSelectedMentality() {
        return selectedMentality;
    }

    public void setSelectedMentality(Mentality selectedMentality) {
        this.selectedMentality = selectedMentality;
    }

    public Integer getFormationScore() {
        return formationScore;
    }

    public void setFormationScore(Integer formationScore) {
        this.formationScore = formationScore;
    }

    public List<TacticalExplanationStep> getExplanationSteps() {
        return explanationSteps;
    }

    public void setExplanationSteps(List<TacticalExplanationStep> explanationSteps) {
        this.explanationSteps = explanationSteps != null ? explanationSteps : new ArrayList<>();
    }

    public void addExplanationStep(TacticalExplanationStep explanationStep) {
        this.explanationSteps.add(explanationStep);
    }

    @Override
    public String toString() {
        return "Level2Facts{" +
                "selectedFormation=" + selectedFormation +
                ", selectedMentality=" + selectedMentality +
                ", formationScore=" + formationScore +
                ", explanationSteps=" + explanationSteps +
                '}';
    }
}
