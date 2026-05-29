package com.ftn.sbnz.model.enums;

public enum Formation {
    FORMATION_343("3-4-3"),
    FORMATION_352("3-5-2"),
    FORMATION_3412("3-4-1-2"),
    FORMATION_442("4-4-2"),
    FORMATION_433("4-3-3"),
    FORMATION_4231("4-2-3-1"),
    FORMATION_4141("4-1-4-1"),
    FORMATION_4312("4-3-1-2"),
    FORMATION_451("4-5-1"),
    FORMATION_4123("4-1-2-3"),
    FORMATION_532("5-3-2"),
    FORMATION_523("5-2-3"),
    FORMATION_541("5-4-1");

    private final String displayName;

    Formation(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
