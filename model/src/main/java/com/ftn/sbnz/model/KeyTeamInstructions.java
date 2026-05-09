package com.ftn.sbnz.model;

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

    public enum PassingDirectness {
        SHORTER("Shorter"),
        STANDARD("Standard"),
        DIRECT("Direct");

        private final String displayName;

        PassingDirectness(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum PressingIntensity {
        HIGH("High"),
        MEDIUM("Medium"),
        LOW("Low");

        private final String displayName;

        PressingIntensity(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum DefensiveLineHeight {
        HIGH("High"),
        STANDARD("Standard"),
        LOW("Low");

        private final String displayName;

        DefensiveLineHeight(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum TransitionAfterLossOfBall {
        COUNTER_PRESS("Counter-Press"),
        REGROUP("Regroup"),
        HOLD_SHAPE("Hold Shape");

        private final String displayName;

        TransitionAfterLossOfBall(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
