package com.ftn.sbnz.model;

public class RealTimeParameters {
    private Integer currentMinute;
    private MatchResult currentResult;
    private Integer ownTeamYellowCards;
    private Integer ownTeamRedCards;
    private Integer opponentYellowCards;
    private Integer opponentRedCards;

    public RealTimeParameters() {
    }

    public RealTimeParameters(Integer currentMinute, MatchResult currentResult,
            Integer ownTeamYellowCards, Integer ownTeamRedCards,
            Integer opponentYellowCards, Integer opponentRedCards) {
        this.currentMinute = currentMinute;
        this.currentResult = currentResult;
        this.ownTeamYellowCards = ownTeamYellowCards;
        this.ownTeamRedCards = ownTeamRedCards;
        this.opponentYellowCards = opponentYellowCards;
        this.opponentRedCards = opponentRedCards;
    }

    public Integer getCurrentMinute() {
        return currentMinute;
    }

    public void setCurrentMinute(Integer currentMinute) {
        this.currentMinute = currentMinute;
    }

    public MatchResult getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(MatchResult currentResult) {
        this.currentResult = currentResult;
    }

    public Integer getOwnTeamYellowCards() {
        return ownTeamYellowCards;
    }

    public void setOwnTeamYellowCards(Integer ownTeamYellowCards) {
        this.ownTeamYellowCards = ownTeamYellowCards;
    }

    public Integer getOwnTeamRedCards() {
        return ownTeamRedCards;
    }

    public void setOwnTeamRedCards(Integer ownTeamRedCards) {
        this.ownTeamRedCards = ownTeamRedCards;
    }

    public Integer getOpponentYellowCards() {
        return opponentYellowCards;
    }

    public void setOpponentYellowCards(Integer opponentYellowCards) {
        this.opponentYellowCards = opponentYellowCards;
    }

    public Integer getOpponentRedCards() {
        return opponentRedCards;
    }

    public void setOpponentRedCards(Integer opponentRedCards) {
        this.opponentRedCards = opponentRedCards;
    }

    @Override
    public String toString() {
        return "RealTimeParameters{" +
                "currentMinute=" + currentMinute +
                ", currentResult=" + currentResult +
                ", ownTeamYellowCards=" + ownTeamYellowCards +
                ", ownTeamRedCards=" + ownTeamRedCards +
                ", opponentYellowCards=" + opponentYellowCards +
                ", opponentRedCards=" + opponentRedCards +
                '}';
    }

    public enum MatchResult {
        WINNING,
        DRAW,
        LOSING
    }
}
