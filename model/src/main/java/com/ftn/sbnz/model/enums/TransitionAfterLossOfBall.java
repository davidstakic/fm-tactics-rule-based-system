package com.ftn.sbnz.model.enums;

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
