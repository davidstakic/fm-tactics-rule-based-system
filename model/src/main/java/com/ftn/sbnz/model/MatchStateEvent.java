package com.ftn.sbnz.model;

public class MatchStateEvent {
    private Long timestamp;
    private Integer currentMinute;
    private MatchResult currentResult;
    private Integer ownTeamRedCards;
    private Integer opponentRedCards;

    public MatchStateEvent() {
    }

    public MatchStateEvent(Long timestamp, Integer currentMinute, MatchResult currentResult,
            Integer ownTeamRedCards, Integer opponentRedCards) {
        this.timestamp = timestamp;
        this.currentMinute = currentMinute;
        this.currentResult = currentResult;
        this.ownTeamRedCards = ownTeamRedCards;
        this.opponentRedCards = opponentRedCards;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
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

    public Integer getOwnTeamRedCards() {
        return ownTeamRedCards;
    }

    public void setOwnTeamRedCards(Integer ownTeamRedCards) {
        this.ownTeamRedCards = ownTeamRedCards;
    }

    public Integer getOpponentRedCards() {
        return opponentRedCards;
    }

    public void setOpponentRedCards(Integer opponentRedCards) {
        this.opponentRedCards = opponentRedCards;
    }

    @Override
    public String toString() {
        return "MatchStateEvent{" +
                "timestamp=" + timestamp +
                ", currentMinute=" + currentMinute +
                ", currentResult=" + currentResult +
                ", ownTeamRedCards=" + ownTeamRedCards +
                ", opponentRedCards=" + opponentRedCards +
                '}';
    }
}
