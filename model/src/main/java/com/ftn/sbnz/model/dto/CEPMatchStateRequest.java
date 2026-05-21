package com.ftn.sbnz.model.dto;

import com.ftn.sbnz.model.cep.MatchStateEvent;
import com.ftn.sbnz.model.enums.MatchResult;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class CEPMatchStateRequest {
    @PositiveOrZero(message = "timestamp must be zero or positive.")
    private Long timestamp;

    @NotNull(message = "currentMinute is required.")
    @Min(value = 0, message = "currentMinute must be at least 0.")
    @Max(value = 120, message = "currentMinute must be at most 120.")
    private Integer currentMinute;

    @NotNull(message = "currentResult is required.")
    private MatchResult currentResult;

    @PositiveOrZero(message = "ownTeamRedCards must be zero or positive.")
    private Integer ownTeamRedCards;

    @PositiveOrZero(message = "opponentRedCards must be zero or positive.")
    private Integer opponentRedCards;

    public CEPMatchStateRequest() {
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

    public MatchStateEvent toMatchStateEvent() {
        return new MatchStateEvent(
                timestamp != null ? timestamp : System.currentTimeMillis(),
                currentMinute,
                currentResult,
                ownTeamRedCards != null ? ownTeamRedCards : 0,
                opponentRedCards != null ? opponentRedCards : 0);
    }
}
