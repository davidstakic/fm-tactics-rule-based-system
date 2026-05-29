package com.ftn.sbnz.model.dto;

import com.ftn.sbnz.model.forward.MatchContext;
import com.ftn.sbnz.model.forward.OpponentProfile;
import com.ftn.sbnz.model.forward.TacticalAssistantInput;
import com.ftn.sbnz.model.forward.TeamProfile;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class ForwardChainingRequest {
    @Valid
    @NotNull(message = "Own team profile is required.")
    private TeamProfileRequest teamProfile;

    @Valid
    @NotNull(message = "Opponent profile is required.")
    private OpponentProfileRequest opponentProfile;

    @Valid
    @NotNull(message = "Match context is required.")
    private MatchContextRequest matchContext;

    public ForwardChainingRequest() {
    }

    public TeamProfileRequest getTeamProfile() {
        return teamProfile;
    }

    public void setTeamProfile(TeamProfileRequest teamProfile) {
        this.teamProfile = teamProfile;
    }

    public OpponentProfileRequest getOpponentProfile() {
        return opponentProfile;
    }

    public void setOpponentProfile(OpponentProfileRequest opponentProfile) {
        this.opponentProfile = opponentProfile;
    }

    public MatchContextRequest getMatchContext() {
        return matchContext;
    }

    public void setMatchContext(MatchContextRequest matchContext) {
        this.matchContext = matchContext;
    }

    public TacticalAssistantInput toTacticalAssistantInput() {
        TeamProfile tacticalTeamProfile = teamProfile.toTeamProfile();
        OpponentProfile tacticalOpponentProfile = opponentProfile.toOpponentProfile();
        MatchContext tacticalMatchContext = matchContext.toMatchContext();
        return new TacticalAssistantInput(tacticalTeamProfile, tacticalOpponentProfile, tacticalMatchContext);
    }
}
