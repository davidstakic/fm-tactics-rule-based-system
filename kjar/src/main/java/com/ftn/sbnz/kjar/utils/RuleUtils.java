package com.ftn.sbnz.kjar.utils;

import com.ftn.sbnz.model.forward.FormationScore;
import com.ftn.sbnz.model.enums.Mentality;
import com.ftn.sbnz.model.enums.TeamSide;
import java.util.List;

public final class RuleUtils {

    private RuleUtils() {
    }

    public static void addScoreToFormations(
            List<?> scores,
            String[] formations,
            String description,
            int points) {
        for (String formation : formations) {
            for (Object obj : scores) {
                FormationScore score = (FormationScore) obj;

                if (score.getFormation().name().equals(formation)) {
                    score.addScore(description, points);
                    break;
                }
            }
        }
    }

    public static int countWinsInForm(String formLast5Matches) {
        if (formLast5Matches == null || formLast5Matches.trim().isEmpty()) {
            return 0;
        }

        int wins = 0;
        for (String result : formLast5Matches.split("-")) {
            if ("W".equals(result.trim())) {
                wins++;
            }
        }
        return wins;
    }

    public static Mentality increaseMentality(Mentality current, int steps) {
        if (current == null) {
            return Mentality.POSITIVE;
        }

        Mentality[] values = Mentality.values();
        int nextIndex = current.ordinal() + steps;
        if (nextIndex >= values.length) {
            nextIndex = values.length - 1;
        }

        return values[nextIndex];
    }

    public static Mentality decreaseMentality(Mentality current, int steps) {
        if (current == null) {
            return Mentality.CAUTIOUS;
        }

        int nextIndex = current.ordinal() - steps;
        if (nextIndex < 0) {
            nextIndex = 0;
        }

        return Mentality.values()[nextIndex];
    }

    public static String redCardRecommendationType(TeamSide teamSide, Integer redCardNumber) {
        String prefix = teamSide == TeamSide.OPPONENT ? "OPPONENT_RED_CARD_" : "OWN_TEAM_RED_CARD_";
        return prefix + redCardNumber;
    }
}
