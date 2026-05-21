package com.ftn.sbnz.model.forward;

import com.ftn.sbnz.model.enums.DefensiveLineHeight;
import com.ftn.sbnz.model.enums.PassingDirectness;
import com.ftn.sbnz.model.enums.PressingIntensity;
import com.ftn.sbnz.model.enums.TransitionAfterLossOfBall;

public class Level3Facts {
    private PressingIntensity pressingIntensity;
    private DefensiveLineHeight defensiveLineHeight;
    private PassingDirectness passingDirectness;
    private TransitionAfterLossOfBall transitionAfterLossOfBall;

    public Level3Facts() {
    }

    public Level3Facts(PressingIntensity pressingIntensity,
            DefensiveLineHeight defensiveLineHeight,
            PassingDirectness passingDirectness,
            TransitionAfterLossOfBall transitionAfterLossOfBall) {
        this.pressingIntensity = pressingIntensity;
        this.defensiveLineHeight = defensiveLineHeight;
        this.passingDirectness = passingDirectness;
        this.transitionAfterLossOfBall = transitionAfterLossOfBall;
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

    public PassingDirectness getPassingDirectness() {
        return passingDirectness;
    }

    public void setPassingDirectness(PassingDirectness passingDirectness) {
        this.passingDirectness = passingDirectness;
    }

    public TransitionAfterLossOfBall getTransitionAfterLossOfBall() {
        return transitionAfterLossOfBall;
    }

    public void setTransitionAfterLossOfBall(TransitionAfterLossOfBall transitionAfterLossOfBall) {
        this.transitionAfterLossOfBall = transitionAfterLossOfBall;
    }

    @Override
    public String toString() {
        return "Level3Facts{" +
                "pressingIntensity=" + pressingIntensity +
                ", defensiveLineHeight=" + defensiveLineHeight +
                ", passingDirectness=" + passingDirectness +
                ", transitionAfterLossOfBall=" + transitionAfterLossOfBall +
                '}';
    }
}
