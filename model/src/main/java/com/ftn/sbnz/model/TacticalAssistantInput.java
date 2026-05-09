package com.ftn.sbnz.model;

public class TacticalAssistantInput {
    private TeamProfile teamProfile;
    private OpponentProfile opponentProfile;
    private MatchContext matchContext;
    private RealTimeParameters realTimeParameters;

    public TacticalAssistantInput() {
    }

    public TacticalAssistantInput(TeamProfile teamProfile, OpponentProfile opponentProfile,
            MatchContext matchContext, RealTimeParameters realTimeParameters) {
        this.teamProfile = teamProfile;
        this.opponentProfile = opponentProfile;
        this.matchContext = matchContext;
        this.realTimeParameters = realTimeParameters;
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

    public RealTimeParameters getRealTimeParameters() {
        return realTimeParameters;
    }

    public void setRealTimeParameters(RealTimeParameters realTimeParameters) {
        this.realTimeParameters = realTimeParameters;
    }

    @Override
    public String toString() {
        return "TacticalAssistantInput{" +
                "teamProfile=" + teamProfile +
                ", opponentProfile=" + opponentProfile +
                ", matchContext=" + matchContext +
                ", realTimeParameters=" + realTimeParameters +
                '}';
    }
}
