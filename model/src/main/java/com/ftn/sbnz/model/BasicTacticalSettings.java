package com.ftn.sbnz.model;

public class BasicTacticalSettings {
    private Formation recommendedFormation;
    private Mentality mentality;

    public BasicTacticalSettings() {
    }

    public BasicTacticalSettings(Formation recommendedFormation, Mentality mentality) {
        this.recommendedFormation = recommendedFormation;
        this.mentality = mentality;
    }

    public Formation getRecommendedFormation() {
        return recommendedFormation;
    }

    public void setRecommendedFormation(Formation recommendedFormation) {
        this.recommendedFormation = recommendedFormation;
    }

    public Mentality getMentality() {
        return mentality;
    }

    public void setMentality(Mentality mentality) {
        this.mentality = mentality;
    }

    @Override
    public String toString() {
        return "BasicTacticalSettings{" +
                "recommendedFormation=" + recommendedFormation +
                ", mentality=" + mentality +
                '}';
    }

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
}
