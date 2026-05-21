package com.ftn.sbnz.model.forward;

import com.ftn.sbnz.model.enums.Formation;
import com.ftn.sbnz.model.enums.Mentality;

public class BasicTacticalSettings {
    private Formation recommendedFormation;
    private Mentality mentality;

    public BasicTacticalSettings() {
    }

    public BasicTacticalSettings(Formation recommendedFormation, Mentality mentality) {
        this.recommendedFormation = recommendedFormation;
        this.mentality = mentality;
    }

    public Formation getRecommendedFormation() {
        return recommendedFormation;
    }

    public void setRecommendedFormation(Formation recommendedFormation) {
        this.recommendedFormation = recommendedFormation;
    }

    public Mentality getMentality() {
        return mentality;
    }

    public void setMentality(Mentality mentality) {
        this.mentality = mentality;
    }

    @Override
    public String toString() {
        return "BasicTacticalSettings{" +
                "recommendedFormation=" + recommendedFormation +
                ", mentality=" + mentality +
                '}';
    }

}
