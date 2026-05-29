package com.ftn.sbnz.model.dto;

import com.ftn.sbnz.model.enums.DefenseLineEngagement;
import com.ftn.sbnz.model.enums.OpponentWeakness;
import com.ftn.sbnz.model.enums.PlayingStyle;
import com.ftn.sbnz.model.forward.OpponentProfile;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class OpponentProfileRequest {
    @NotNull(message = "Opponent strength is required.")
    @DecimalMin(value = "1.0", message = "Opponent strength must be at least 1.0.")
    @DecimalMax(value = "5.0", message = "Opponent strength must be at most 5.0.")
    private Double opponentStrength;

    @NotNull(message = "Opponent playing style is required.")
    private PlayingStyle playingStyle;

    @NotNull(message = "Opponent line of engagement is required.")
    private DefenseLineEngagement lineEngagement;

    @NotNull(message = "Main opponent weakness is required.")
    private OpponentWeakness weakness;

    public OpponentProfileRequest() {
    }

    public Double getOpponentStrength() {
        return opponentStrength;
    }

    public void setOpponentStrength(Double opponentStrength) {
        this.opponentStrength = opponentStrength;
    }

    public PlayingStyle getPlayingStyle() {
        return playingStyle;
    }

    public void setPlayingStyle(PlayingStyle playingStyle) {
        this.playingStyle = playingStyle;
    }

    public DefenseLineEngagement getLineEngagement() {
        return lineEngagement;
    }

    public void setLineEngagement(DefenseLineEngagement lineEngagement) {
        this.lineEngagement = lineEngagement;
    }

    public OpponentWeakness getWeakness() {
        return weakness;
    }

    public void setWeakness(OpponentWeakness weakness) {
        this.weakness = weakness;
    }

    public OpponentProfile toOpponentProfile() {
        return new OpponentProfile(opponentStrength, playingStyle, lineEngagement, weakness);
    }
}
