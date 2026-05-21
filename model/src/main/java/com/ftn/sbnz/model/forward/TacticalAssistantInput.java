package com.ftn.sbnz.model.forward;

public class TacticalAssistantInput {
    private TeamProfile teamProfile;
    private OpponentProfile opponentProfile;
    private MatchContext matchContext;

    public TacticalAssistantInput() {
    }

    public TacticalAssistantInput(TeamProfile teamProfile, OpponentProfile opponentProfile,
            MatchContext matchContext) {
        this.teamProfile = teamProfile;
        this.opponentProfile = opponentProfile;
        this.matchContext = matchContext;
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

    @Override
    public String toString() {
        return "TacticalAssistantInput{" +
                "teamProfile=" + teamProfile +
                ", opponentProfile=" + opponentProfile +
                ", matchContext=" + matchContext +
                '}';
    }
}
