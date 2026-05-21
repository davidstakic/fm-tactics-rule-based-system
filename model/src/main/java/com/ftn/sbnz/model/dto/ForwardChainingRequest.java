package com.ftn.sbnz.model.dto;

import com.ftn.sbnz.model.forward.MatchContext;
import com.ftn.sbnz.model.forward.OpponentProfile;
import com.ftn.sbnz.model.forward.TacticalAssistantInput;
import com.ftn.sbnz.model.forward.TeamProfile;
import jakarta.validation.constraints.NotNull;

public class ForwardChainingRequest {
    @NotNull(message = "teamProfile is required.")
    private TeamProfile teamProfile;

    @NotNull(message = "opponentProfile is required.")
    private OpponentProfile opponentProfile;

    @NotNull(message = "matchContext is required.")
    private MatchContext matchContext;

    public ForwardChainingRequest() {
    }

    public TeamProfile getTeamProfile() {
        return teamProfile;
    }

    public void setTeamProfile(TeamProfile teamProfile) {
        this.teamProfile = teamProfile;
    }

    public OpponentProfile getOpponentProfile() {
        return opponentProfile;
    }

    public void setOpponentProfile(OpponentProfile opponentProfile) {
        this.opponentProfile = opponentProfile;
    }

    public MatchContext getMatchContext() {
        return matchContext;
    }

    public void setMatchContext(MatchContext matchContext) {
        this.matchContext = matchContext;
    }

    public TacticalAssistantInput toTacticalAssistantInput() {
        return new TacticalAssistantInput(teamProfile, opponentProfile, matchContext);
    }
}
