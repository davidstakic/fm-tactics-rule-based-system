package com.ftn.sbnz.model.forward;

import com.ftn.sbnz.model.enums.DefensiveLineHeight;
import com.ftn.sbnz.model.enums.PassingDirectness;
import com.ftn.sbnz.model.enums.PressingIntensity;
import com.ftn.sbnz.model.enums.TransitionAfterLossOfBall;

public class KeyTeamInstructions {
    private PassingDirectness passingDirectness;
    private PressingIntensity pressingIntensity;
    private DefensiveLineHeight defensiveLineHeight;
    private TransitionAfterLossOfBall transition;

    public KeyTeamInstructions() {
    }

    public KeyTeamInstructions(PassingDirectness passingDirectness, PressingIntensity pressingIntensity,
            DefensiveLineHeight defensiveLineHeight, TransitionAfterLossOfBall transition) {
        this.passingDirectness = passingDirectness;
        this.pressingIntensity = pressingIntensity;
        this.defensiveLineHeight = defensiveLineHeight;
        this.transition = transition;
    }

    public PassingDirectness getPassingDirectness() {
        return passingDirectness;
    }

    public void setPassingDirectness(PassingDirectness passingDirectness) {
        this.passingDirectness = passingDirectness;
    }

    public PressingIntensity getPressingIntensity() {
        return pressingIntensity;
    }

    public void setPressingIntensity(PressingIntensity pressingIntensity) {
        this.pressingIntensity = pressingIntensity;
    }

    public DefensiveLineHeight getDefensiveLineHeight() {
        return defensiveLineHeight;
    }

    public void setDefensiveLineHeight(DefensiveLineHeight defensiveLineHeight) {
        this.defensiveLineHeight = defensiveLineHeight;
    }

    public TransitionAfterLossOfBall getTransition() {
        return transition;
    }

    public void setTransition(TransitionAfterLossOfBall transition) {
        this.transition = transition;
    }

    @Override
    public String toString() {
        return "KeyTeamInstructions{" +
                "passingDirectness=" + passingDirectness +
                ", pressingIntensity=" + pressingIntensity +
                ", defensiveLineHeight=" + defensiveLineHeight +
                ", transition=" + transition +
                '}';
    }

}
