package com.ftn.sbnz.model.enums;

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
