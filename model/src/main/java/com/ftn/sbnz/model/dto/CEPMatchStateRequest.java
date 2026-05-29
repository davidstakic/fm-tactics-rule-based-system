package com.ftn.sbnz.model.dto;

import com.ftn.sbnz.model.cep.MatchStateEvent;
import com.ftn.sbnz.model.enums.MatchResult;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class CEPMatchStateRequest {
    @PositiveOrZero(message = "Match state timestamp must be zero or positive.")
    private Long timestamp;

    @NotNull(message = "Current minute is required.")
    @Min(value = 0, message = "Current minute must be at least 0.")
    @Max(value = 120, message = "Current minute must be at most 120.")
    private Integer currentMinute;

    @NotNull(message = "Current result is required.")
    private MatchResult currentResult;

    @NotNull(message = "Own team red card count is required.")
    @PositiveOrZero(message = "Own team red card count must be zero or positive.")
    private Integer ownTeamRedCards;

    @NotNull(message = "Opponent red card count is required.")
    @PositiveOrZero(message = "Opponent red card count must be zero or positive.")
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
