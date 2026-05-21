package com.ftn.sbnz.model.cep;

import com.ftn.sbnz.model.enums.DefensiveLineHeight;
import com.ftn.sbnz.model.enums.Mentality;
import com.ftn.sbnz.model.enums.PassingDirectness;
import com.ftn.sbnz.model.enums.PressingIntensity;
import com.ftn.sbnz.model.enums.TransitionAfterLossOfBall;

public class CEPRecommendation {
    private String eventType;
    private String eventDescription;
    private Mentality adjustedMentality;
    private PassingDirectness adjustedPassing;
    private PressingIntensity adjustedPressing;
    private DefensiveLineHeight adjustedDefensiveLineHeight;
    private TransitionAfterLossOfBall adjustedTransition;
    private String explanation;

    public CEPRecommendation() {
    }

    public CEPRecommendation(String eventType, String eventDescription, String explanation) {
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.explanation = explanation;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Mentality getAdjustedMentality() {
        return adjustedMentality;
    }

    public void setAdjustedMentality(Mentality adjustedMentality) {
        this.adjustedMentality = adjustedMentality;
    }

    public PassingDirectness getAdjustedPassing() {
        return adjustedPassing;
    }

    public void setAdjustedPassing(PassingDirectness adjustedPassing) {
        this.adjustedPassing = adjustedPassing;
    }

    public PressingIntensity getAdjustedPressing() {
        return adjustedPressing;
    }

    public void setAdjustedPressing(PressingIntensity adjustedPressing) {
        this.adjustedPressing = adjustedPressing;
    }

    public DefensiveLineHeight getAdjustedDefensiveLineHeight() {
        return adjustedDefensiveLineHeight;
    }

    public void setAdjustedDefensiveLineHeight(DefensiveLineHeight adjustedDefensiveLineHeight) {
        this.adjustedDefensiveLineHeight = adjustedDefensiveLineHeight;
    }

    public TransitionAfterLossOfBall getAdjustedTransition() {
        return adjustedTransition;
    }

    public void setAdjustedTransition(TransitionAfterLossOfBall adjustedTransition) {
        this.adjustedTransition = adjustedTransition;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Override
    public String toString() {
        return "CEPRecommendation{" +
                "eventType='" + eventType + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", adjustedMentality=" + adjustedMentality +
                ", adjustedPassing=" + adjustedPassing +
                ", adjustedPressing=" + adjustedPressing +
                ", adjustedDefensiveLineHeight=" + adjustedDefensiveLineHeight +
                ", adjustedTransition=" + adjustedTransition +
                ", explanation='" + explanation + '\'' +
                '}';
    }
}
