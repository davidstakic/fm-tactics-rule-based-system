package com.ftn.sbnz.model.dto;

import com.ftn.sbnz.model.enums.CompetitionType;
import com.ftn.sbnz.model.enums.LocationType;
import com.ftn.sbnz.model.enums.MatchImportance;
import com.ftn.sbnz.model.forward.MatchContext;
import jakarta.validation.constraints.NotNull;

public class MatchContextRequest {
    @NotNull(message = "Competition type is required.")
    private CompetitionType competitionType;

    @NotNull(message = "Match importance is required.")
    private MatchImportance importance;

    @NotNull(message = "Match location is required.")
    private LocationType location;

    public MatchContextRequest() {
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

    public MatchContext toMatchContext() {
        return new MatchContext(competitionType, importance, location);
    }
}
