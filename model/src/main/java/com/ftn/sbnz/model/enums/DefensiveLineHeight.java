package com.ftn.sbnz.model.enums;

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
