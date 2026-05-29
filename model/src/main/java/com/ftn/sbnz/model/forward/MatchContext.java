package com.ftn.sbnz.model.forward;

import com.ftn.sbnz.model.enums.CompetitionType;
import com.ftn.sbnz.model.enums.LocationType;
import com.ftn.sbnz.model.enums.MatchImportance;

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

}
