package com.ftn.sbnz.model.forward;

import com.ftn.sbnz.model.enums.Formation;
import com.ftn.sbnz.model.enums.Mentality;

public class Level2Facts {
    private Formation selectedFormation;
    private Mentality selectedMentality;
    private Integer formationScore;

    public Level2Facts() {
    }

    public Level2Facts(Formation selectedFormation,
            Mentality selectedMentality,
            Integer formationScore) {
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

    @Override
    public String toString() {
        return "Level2Facts{" +
                "selectedFormation=" + selectedFormation +
                ", selectedMentality=" + selectedMentality +
                ", formationScore=" + formationScore +
                '}';
    }
}
