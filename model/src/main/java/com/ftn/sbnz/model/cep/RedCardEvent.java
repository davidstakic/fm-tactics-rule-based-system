package com.ftn.sbnz.model.cep;

import com.ftn.sbnz.model.enums.TeamSide;

public class RedCardEvent {
    private Long timestamp;
    private Integer minute;
    private TeamSide teamSide;
    private Integer redCardNumber;

    public RedCardEvent() {
    }

    public RedCardEvent(Long timestamp, Integer minute, TeamSide teamSide, Integer redCardNumber) {
        this.timestamp = timestamp;
        this.minute = minute;
        this.teamSide = teamSide;
        this.redCardNumber = redCardNumber;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public TeamSide getTeamSide() {
        return teamSide;
    }

    public void setTeamSide(TeamSide teamSide) {
        this.teamSide = teamSide;
    }

    public Integer getRedCardNumber() {
        return redCardNumber;
    }

    public void setRedCardNumber(Integer redCardNumber) {
        this.redCardNumber = redCardNumber;
    }

    @Override
    public String toString() {
        return "RedCardEvent{" +
                "timestamp=" + timestamp +
                ", minute=" + minute +
                ", teamSide=" + teamSide +
                ", redCardNumber=" + redCardNumber +
                '}';
    }
}
