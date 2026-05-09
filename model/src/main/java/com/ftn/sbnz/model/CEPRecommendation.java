package com.ftn.sbnz.model;

public class CEPRecommendation {
    private String eventType;
    private String eventDescription;
    private BasicTacticalSettings.Mentality adjustedMentality;
    private KeyTeamInstructions.PassingDirectness adjustedPassing;
    private KeyTeamInstructions.PressingIntensity adjustedPressing;
    private KeyTeamInstructions.DefensiveLineHeight adjustedDefensiveLineHeight;
    private KeyTeamInstructions.TransitionAfterLossOfBall adjustedTransition;
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

    public BasicTacticalSettings.Mentality getAdjustedMentality() {
        return adjustedMentality;
    }

    public void setAdjustedMentality(BasicTacticalSettings.Mentality adjustedMentality) {
        this.adjustedMentality = adjustedMentality;
    }

    public KeyTeamInstructions.PassingDirectness getAdjustedPassing() {
        return adjustedPassing;
    }

    public void setAdjustedPassing(KeyTeamInstructions.PassingDirectness adjustedPassing) {
        this.adjustedPassing = adjustedPassing;
    }

    public KeyTeamInstructions.PressingIntensity getAdjustedPressing() {
        return adjustedPressing;
    }

    public void setAdjustedPressing(KeyTeamInstructions.PressingIntensity adjustedPressing) {
        this.adjustedPressing = adjustedPressing;
    }

    public KeyTeamInstructions.DefensiveLineHeight getAdjustedDefensiveLineHeight() {
        return adjustedDefensiveLineHeight;
    }

    public void setAdjustedDefensiveLineHeight(KeyTeamInstructions.DefensiveLineHeight adjustedDefensiveLineHeight) {
        this.adjustedDefensiveLineHeight = adjustedDefensiveLineHeight;
    }

    public KeyTeamInstructions.TransitionAfterLossOfBall getAdjustedTransition() {
        return adjustedTransition;
    }

    public void setAdjustedTransition(KeyTeamInstructions.TransitionAfterLossOfBall adjustedTransition) {
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
