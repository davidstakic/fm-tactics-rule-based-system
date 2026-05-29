package com.ftn.sbnz.model.enums;

public enum Mentality {
    VERY_DEFENSIVE("Very Defensive"),
    DEFENSIVE("Defensive"),
    CAUTIOUS("Cautious"),
    BALANCED("Balanced"),
    POSITIVE("Positive"),
    ATTACKING("Attacking"),
    VERY_ATTACKING("Very Attacking");

    private final String displayName;

    Mentality(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
