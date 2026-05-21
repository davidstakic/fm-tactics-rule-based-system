package com.ftn.sbnz.model.forward;

import com.ftn.sbnz.model.enums.DefenseLineEngagement;
import com.ftn.sbnz.model.enums.OpponentWeakness;
import com.ftn.sbnz.model.enums.PlayingStyle;

public class OpponentProfile {
    private Double opponentStrength;
    private PlayingStyle playingStyle;
    private DefenseLineEngagement lineEngagement;
    private OpponentWeakness weakness;

    public OpponentProfile() {
    }

    public OpponentProfile(Double opponentStrength, PlayingStyle playingStyle,
            DefenseLineEngagement lineEngagement, OpponentWeakness weakness) {
        this.opponentStrength = opponentStrength;
        this.playingStyle = playingStyle;
        this.lineEngagement = lineEngagement;
        this.weakness = weakness;
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

    @Override
    public String toString() {
        return "OpponentProfile{" +
                "opponentStrength=" + opponentStrength +
                ", playingStyle=" + playingStyle +
                ", lineEngagement=" + lineEngagement +
                ", weakness=" + weakness +
                '}';
    }

}
