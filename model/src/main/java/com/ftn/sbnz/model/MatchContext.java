package com.ftn.sbnz.model;

public class MatchContext {
    private CompetitionType competitionType;
    private MatchImportance importance;
    private LocationType location;

    public MatchContext() {
    }

    public MatchContext(CompetitionType competitionType, MatchImportance importance, LocationType location) {
        this.competitionType = competitionType;
        this.importance = importance;
        this.location = location;
    }

    public CompetitionType getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(CompetitionType competitionType) {
        this.competitionType = competitionType;
    }

    public MatchImportance getImportance() {
        return importance;
    }

    public void setImportance(MatchImportance importance) {
        this.importance = importance;
    }

    public LocationType getLocation() {
        return location;
    }

    public void setLocation(LocationType location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "MatchContext{" +
                "competitionType=" + competitionType +
                ", importance=" + importance +
                ", location=" + location +
                '}';
    }

    public enum CompetitionType {
        FRIENDLY,
        LEAGUE,
        CUP,
        CONTINENTAL
    }

    public enum MatchImportance {
        LOW,
        MEDIUM,
        HIGH
    }

    public enum LocationType {
        HOME,
        AWAY,
        NEUTRAL
    }
}
