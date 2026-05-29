package com.ftn.sbnz.service.service;

import com.ftn.sbnz.kjar.backward.TacticalGoalTree;
import com.ftn.sbnz.model.backward.TacticalGoal;
import com.ftn.sbnz.model.dto.BackwardRequirement;
import com.ftn.sbnz.model.dto.BackwardChainingResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.api.runtime.rule.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BackwardChainingService {
    private final KieBase backwardChainingKieBase;

    @Autowired
    public BackwardChainingService(@Qualifier("backwardChainingKieBase") KieBase backwardChainingKieBase) {
        this.backwardChainingKieBase = backwardChainingKieBase;
    }

    public BackwardChainingResponse explain(String targetGoal) {
        if (targetGoal == null || targetGoal.trim().isEmpty()) {
            throw new IllegalArgumentException("Choose a tactical idea to check.");
        }

        KieSession kieSession = backwardChainingKieBase.newKieSession();
        try {
            for (TacticalGoal tacticalGoal : TacticalGoalTree.createTacticalGoals()) {
                kieSession.insert(tacticalGoal);
            }

            List<TacticalGoal> requirements = findRequirementsForGoal(kieSession, targetGoal.trim());
            if (requirements.isEmpty()) {
                throw new IllegalArgumentException("No supporting conditions were found for the selected tactical idea.");
            }

            return new BackwardChainingResponse(
                    toTargetGoalTitle(targetGoal.trim()),
                    "These are supporting conditions for this tactical idea. They are not all required at the same time.",
                    toBackwardRequirements(requirements));
        } finally {
            kieSession.dispose();
        }
    }

    private List<TacticalGoal> findRequirementsForGoal(KieSession kieSession, String targetGoal) {
        QueryResults results = kieSession.getQueryResults(
                "requirementsForGoal",
                targetGoal,
                Variable.v,
                Variable.v,
                Variable.v);

        Map<String, TacticalGoal> uniqueRequirements = new LinkedHashMap<>();
        for (QueryResultsRow row : results) {
            String requirement = (String) row.get("$requirement");
            String level = (String) row.get("$level");
            String explanation = (String) row.get("$explanation");
            uniqueRequirements.putIfAbsent(
                    targetGoal + "|" + requirement + "|" + level + "|" + explanation,
                    new TacticalGoal(targetGoal, requirement, level, explanation));
        }

        List<TacticalGoal> sortedRequirements = new ArrayList<>(uniqueRequirements.values());
        sortedRequirements.sort(Comparator
                .comparing(TacticalGoal::getLevel)
                .thenComparing(TacticalGoal::getRequirement));
        return sortedRequirements;
    }

    private List<BackwardRequirement> toBackwardRequirements(List<TacticalGoal> tacticalGoals) {
        Map<String, BackwardRequirement> uniqueRequirements = new LinkedHashMap<>();
        for (TacticalGoal tacticalGoal : tacticalGoals) {
            String requirement = tacticalGoal.getRequirement();
            if (requirement == null || !requirement.startsWith("INPUT:")) {
                continue;
            }

            BackwardRequirement backwardRequirement = toInputRequirement(requirement, tacticalGoal.getExplanation());
            uniqueRequirements.putIfAbsent(
                    backwardRequirement.getTitle() + "|" + backwardRequirement.getDescription(),
                    backwardRequirement);
        }
        return new ArrayList<>(uniqueRequirements.values());
    }

    private BackwardRequirement toInputRequirement(String requirement, String fallbackExplanation) {
        switch (requirement) {
            case "INPUT:teamProfile.teamStrength - opponentProfile.opponentStrength > 0.8":
                return new BackwardRequirement("Team quality advantage", "Your team should be clearly stronger than the opponent.");
            case "INPUT:opponentProfile.opponentStrength - teamProfile.teamStrength > 0.8":
                return new BackwardRequirement("Opponent quality advantage", "The opponent should be clearly stronger, so the plan needs extra protection.");
            case "INPUT:abs(teamProfile.teamStrength - opponentProfile.opponentStrength) <= 0.8":
                return new BackwardRequirement("Similar team strength", "The two teams should be close enough in quality for a balanced tactical plan to make sense.");
            case "INPUT:teamProfile.formLast5Matches has at least 3 wins":
                return new BackwardRequirement("Strong recent results", "The team should have at least three wins in the last five matches.");
            case "INPUT:teamProfile.formLast5Matches has at most 1 win":
                return new BackwardRequirement("Weak recent results", "The team should have at most one win in the last five matches, which calls for a safer plan.");
            case "INPUT:teamProfile.formLast5Matches has exactly 2 wins":
                return new BackwardRequirement("Mixed recent results", "The team should have exactly two wins in the last five matches, suggesting neither strong nor poor form.");
            case "INPUT:teamProfile.tacticalFitness >= 70":
                return new BackwardRequirement("High tactical familiarity", "The team's tactical familiarity should be at least 70.");
            case "INPUT:40 <= teamProfile.tacticalFitness <= 69":
                return new BackwardRequirement("Medium tactical familiarity", "The team's tactical familiarity should be between 40 and 69.");
            case "INPUT:teamProfile.tacticalFitness < 40":
                return new BackwardRequirement("Low tactical familiarity", "The team's tactical familiarity should be below 40, so simple instructions are safer.");
            case "INPUT:opponentProfile.weakness == WEAK_AERIAL_DEFENSE":
                return new BackwardRequirement("Opponent weak in the air", "The opponent should struggle with aerial duels.");
            case "INPUT:opponentProfile.weakness == UNRELIABLE_GOALKEEPER":
                return new BackwardRequirement("Unreliable opponent goalkeeper", "The opponent goalkeeper should be a weakness that can be tested with pressure and shots.");
            case "INPUT:matchContext.location == HOME":
                return new BackwardRequirement("Home match", "The match should be played at home.");
            case "INPUT:matchContext.location == AWAY":
                return new BackwardRequirement("Away match", "The match should be played away from home.");
            case "INPUT:matchContext.importance == LOW":
                return new BackwardRequirement("Low match importance", "The match importance should be low.");
            case "INPUT:matchContext.importance == MEDIUM":
                return new BackwardRequirement("Medium match importance", "The match should have medium importance, where a draw can still be acceptable.");
            case "INPUT:matchContext.importance == HIGH":
                return new BackwardRequirement("High match importance", "The match should be important enough that the result strongly shapes the tactical choice.");
            case "INPUT:matchContext.competitionType == FRIENDLY":
                return new BackwardRequirement("Friendly competition", "The match should be a friendly.");
            case "INPUT:matchContext.competitionType == CUP":
                return new BackwardRequirement("Cup match", "The match should be a cup game where winning is usually required.");
            case "INPUT:matchContext.competitionType == LEAGUE":
                return new BackwardRequirement("League match", "The match should be a league game where game-state control can matter as much as risk.");
            case "INPUT:opponentProfile.weakness == SLOW_DEFENDERS":
                return new BackwardRequirement("Slow opponent defenders", "The opponent should have slow defenders who can be attacked with quick transitions.");
            case "INPUT:opponentProfile.weakness == VULNERABLE_ON_FLANKS":
                return new BackwardRequirement("Opponent vulnerable on the flanks", "The opponent should leave useful space or weaknesses in wide areas.");
            case "INPUT:opponentProfile.lineEngagement == HIGH_PRESS":
                return new BackwardRequirement("Opponent presses high", "The opponent should engage high up the pitch.");
            case "INPUT:opponentProfile.lineEngagement == LOW_BLOCK":
                return new BackwardRequirement("Opponent defends deep", "The opponent should defend in a lower block.");
            case "INPUT:opponentProfile.playingStyle == POSSESSION_BASED":
                return new BackwardRequirement("Possession-based opponent", "The opponent should try to control the ball through possession.");
            case "INPUT:opponentProfile.playingStyle != COUNTER_ATTACK":
                return new BackwardRequirement("Opponent is not counter-focused", "The opponent should not primarily rely on counter-attacks.");
            case "INPUT:teamProfile.attackType == WING_PLAY":
                return new BackwardRequirement("Wide attacking plan", "The team should be comfortable attacking through wide areas.");
            case "INPUT:teamProfile.attackType == CENTRAL_PLAY":
                return new BackwardRequirement("Central attacking plan", "The team should be comfortable building attacks through central areas.");
            case "INPUT:teamProfile.attackType == PRESSING_ATTACKERS":
                return new BackwardRequirement("Pressing attackers", "The team should have attackers who can lead the press and attack quickly after turnovers.");
            case "INPUT:teamProfile.attackType != PRESSING_ATTACKERS":
                return new BackwardRequirement("Attack is not built around pressing forwards", "The team should not depend on forwards constantly leading the press.");
            case "INPUT:teamProfile.midfieldQuality == CREATIVE":
                return new BackwardRequirement("Creative midfield", "The midfield should have enough creativity to progress the ball and create chances.");
            case "INPUT:teamProfile.midfieldQuality == AGGRESSIVE":
                return new BackwardRequirement("Aggressive midfield", "The midfield should be strong in duels and able to protect central areas.");
            case "INPUT:teamProfile.midfieldQuality == BALANCED":
                return new BackwardRequirement("Balanced midfield", "The midfield should offer a stable mix of control and protection.");
            case "INPUT:teamProfile.physicalProfile == FAST":
                return new BackwardRequirement("Fast physical profile", "The team should have enough pace to press, recover, and attack space.");
            case "INPUT:teamProfile.physicalProfile != FAST":
                return new BackwardRequirement("No clear pace advantage", "The team should not rely on pace as its main physical advantage.");
            case "INPUT:teamProfile.highLineCapability == true":
                return new BackwardRequirement("Can play a higher defensive line", "The defenders should be comfortable holding a higher line.");
            case "INPUT:teamProfile.highLineCapability == false":
                return new BackwardRequirement("Cannot safely play a high line", "The defenders should not be asked to hold a high line if they are not suited to it.");
            default:
                return toGenericRequirement(requirement, fallbackExplanation);
        }
    }

    private BackwardRequirement toGenericRequirement(String requirement, String fallbackExplanation) {
        return new BackwardRequirement(
                toReadableCode(requirement),
                fallbackExplanation != null ? fallbackExplanation : "This condition supports the selected tactical idea.");
    }

    private String toTargetGoalTitle(String targetGoal) {
        if (targetGoal.startsWith("FORMATION:FORMATION_")) {
            return toFormationTitle(targetGoal.substring("FORMATION:FORMATION_".length())) + " formation";
        }
        if (targetGoal.startsWith("MENTALITY:")) {
            return toTitleCase(targetGoal.substring("MENTALITY:".length())) + " mentality";
        }
        if (targetGoal.startsWith("PRESSING:")) {
            return toTitleCase(targetGoal.substring("PRESSING:".length())) + " pressing";
        }
        if (targetGoal.startsWith("DEFENSIVE_LINE:")) {
            return toTitleCase(targetGoal.substring("DEFENSIVE_LINE:".length())) + " defensive line";
        }
        if (targetGoal.startsWith("PASSING:")) {
            return toTitleCase(targetGoal.substring("PASSING:".length())) + " passing";
        }
        if (targetGoal.startsWith("TRANSITION:")) {
            return toTitleCase(targetGoal.substring("TRANSITION:".length()).replace("AFTER_LOSS_OF_BALL", "")) + " transition";
        }
        return toReadableCode(targetGoal);
    }

    private String toFormationTitle(String formationCode) {
        switch (formationCode) {
            case "343":
                return "3-4-3";
            case "352":
                return "3-5-2";
            case "3412":
                return "3-4-1-2";
            case "442":
                return "4-4-2";
            case "433":
                return "4-3-3";
            case "4231":
                return "4-2-3-1";
            case "4141":
                return "4-1-4-1";
            case "4312":
                return "4-3-1-2";
            case "451":
                return "4-5-1";
            case "4123":
                return "4-1-2-3";
            case "532":
                return "5-3-2";
            case "523":
                return "5-2-3";
            case "541":
                return "5-4-1";
            default:
                return formationCode;
        }
    }

    private String toTitleCase(String value) {
        String lower = value.toLowerCase().replace('_', ' ');
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    private String toReadableCode(String code) {
        if (code == null) {
            return "";
        }
        return code
                .replace("FORMATION:FORMATION_", "Formation ")
                .replace("MENTALITY:", "Mentality ")
                .replace("PRESSING:", "Pressing ")
                .replace("DEFENSIVE_LINE:", "Defensive line ")
                .replace("PASSING:", "Passing ")
                .replace("TRANSITION:", "Transition ")
                .replace("LEVEL1:", "")
                .replace("BASE_", "Base ")
                .replace('_', ' ')
                .replace(":", " ")
                .trim();
    }
}
