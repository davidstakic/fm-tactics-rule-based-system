package com.ftn.sbnz.kjar.backward;

import java.util.ArrayList;
import java.util.List;

import com.ftn.sbnz.model.backward.TacticalGoal;
import com.ftn.sbnz.model.enums.DefensiveLineHeight;
import com.ftn.sbnz.model.enums.Formation;
import com.ftn.sbnz.model.enums.Mentality;
import com.ftn.sbnz.model.enums.PassingDirectness;
import com.ftn.sbnz.model.enums.PressingIntensity;
import com.ftn.sbnz.model.enums.TransitionAfterLossOfBall;

public final class TacticalGoalTree {

    private TacticalGoalTree() {
    }

    public static String formationGoal(Formation formation) {
        return "FORMATION:" + formation.name();
    }

    public static String mentalityGoal(Mentality mentality) {
        return "MENTALITY:" + mentality.name();
    }

    public static String pressingGoal(PressingIntensity pressingIntensity) {
        return "PRESSING:" + pressingIntensity.name();
    }

    public static String defensiveLineGoal(DefensiveLineHeight defensiveLineHeight) {
        return "DEFENSIVE_LINE:" + defensiveLineHeight.name();
    }

    public static String passingGoal(PassingDirectness passingDirectness) {
        return "PASSING:" + passingDirectness.name();
    }

    public static String transitionGoal(TransitionAfterLossOfBall transitionAfterLossOfBall) {
        return "TRANSITION:" + transitionAfterLossOfBall.name();
    }

    public static List<TacticalGoal> createTacticalGoals() {
        List<TacticalGoal> tacticalGoals = new ArrayList<>();

        addLevel1InputGoals(tacticalGoals);
        addLevel1DerivedGoals(tacticalGoals);
        addCompositeGoals(tacticalGoals);
        addMentalityGoals(tacticalGoals);
        addFormationScoringGoals(tacticalGoals);
        addPressingGoals(tacticalGoals);
        addDefensiveLineGoals(tacticalGoals);
        addPassingGoals(tacticalGoals);
        addTransitionGoals(tacticalGoals);

        return tacticalGoals;
    }

    private static void addLevel1InputGoals(List<TacticalGoal> tacticalGoals) {
        addGoal(tacticalGoals, "LEVEL1:IsFavorite", "INPUT:teamProfile.teamStrength - opponentProfile.opponentStrength > 0.8", "L0_INPUT", "Team is stronger than the opponent by more than 0.8.");
        addGoal(tacticalGoals, "LEVEL1:IsUnderdog", "INPUT:opponentProfile.opponentStrength - teamProfile.teamStrength > 0.8", "L0_INPUT", "Opponent is stronger than the team by more than 0.8.");
        addGoal(tacticalGoals, "LEVEL1:IsEvenlyMatched", "INPUT:abs(teamProfile.teamStrength - opponentProfile.opponentStrength) <= 0.8", "L0_INPUT", "Team and opponent strengths are close.");
        addGoal(tacticalGoals, "LEVEL1:HasGoodForm", "INPUT:teamProfile.formLast5Matches has at least 3 wins", "L0_INPUT", "Good recent form.");
        addGoal(tacticalGoals, "LEVEL1:HasBadForm", "INPUT:teamProfile.formLast5Matches has at most 1 win", "L0_INPUT", "Bad recent form.");
        addGoal(tacticalGoals, "LEVEL1:HasNeutralForm", "INPUT:teamProfile.formLast5Matches has exactly 2 wins", "L0_INPUT", "Neutral recent form.");
        addGoal(tacticalGoals, "LEVEL1:HasHighFitness", "INPUT:teamProfile.tacticalFitness >= 70", "L0_INPUT", "High tactical familiarity.");
        addGoal(tacticalGoals, "LEVEL1:HasMediumFitness", "INPUT:40 <= teamProfile.tacticalFitness <= 69", "L0_INPUT", "Medium tactical familiarity.");
        addGoal(tacticalGoals, "LEVEL1:HasLowFitness", "INPUT:teamProfile.tacticalFitness < 40", "L0_INPUT", "Low tactical familiarity.");
        addGoal(tacticalGoals, "LEVEL1:HasAerialOpportunity", "INPUT:opponentProfile.weakness == WEAK_AERIAL_DEFENSE", "L0_INPUT", "Opponent is weak in aerial duels.");
        addGoal(tacticalGoals, "LEVEL1:HasShootingOpportunity", "INPUT:opponentProfile.weakness == UNRELIABLE_GOALKEEPER", "L0_INPUT", "Opponent has an unreliable goalkeeper.");
        addGoal(tacticalGoals, "LEVEL1:IsHomeMatch", "INPUT:matchContext.location == HOME", "L0_INPUT", "The match is played at home.");
        addGoal(tacticalGoals, "LEVEL1:IsLowImportanceMatch", "INPUT:matchContext.importance == LOW", "L0_INPUT", "The match importance is low.");
        addGoal(tacticalGoals, "LEVEL1:IsFriendlyMatch", "INPUT:matchContext.competitionType == FRIENDLY", "L0_INPUT", "The competition is friendly.");
    }

    private static void addLevel1DerivedGoals(List<TacticalGoal> tacticalGoals) {
        addAllRequiredGroup(tacticalGoals, "LEVEL1:IsDifficultAwayMatch", "Difficult away match",
                "LEVEL1:IsUnderdog",
                "INPUT:matchContext.location == AWAY",
                "INPUT:matchContext.importance == HIGH");
        addAllRequiredGroup(tacticalGoals, "LEVEL1:MustWin", "Must win",
                "INPUT:matchContext.competitionType == CUP",
                "INPUT:matchContext.importance == HIGH");
        addAllRequiredGroup(tacticalGoals, "LEVEL1:HasDominantPosition", "Dominant position",
                "LEVEL1:IsFavorite",
                "INPUT:matchContext.location == HOME");
        addAllRequiredGroup(tacticalGoals, "LEVEL1:CanDrawMatch", "Can settle for draw",
                "LEVEL1:IsEvenlyMatched",
                "INPUT:matchContext.importance == MEDIUM",
                "INPUT:matchContext.competitionType == LEAGUE");
        addAllRequiredGroup(tacticalGoals, "LEVEL1:IsCautiousMatch", "Cautious match",
                "LEVEL1:IsUnderdog",
                "INPUT:matchContext.importance == HIGH");
        addAnyRequiredGroup(tacticalGoals, "LEVEL1:HasCounterOpportunity", "Counter opportunity",
                "INPUT:opponentProfile.weakness == SLOW_DEFENDERS",
                "INPUT:opponentProfile.weakness == VULNERABLE_ON_FLANKS");
        addAllRequiredGroup(tacticalGoals, "LEVEL1:IsAggressiveOpponent", "Aggressive opponent",
                "INPUT:opponentProfile.lineEngagement == HIGH_PRESS",
                "INPUT:opponentProfile.playingStyle == POSSESSION_BASED");
        addAllRequiredGroup(tacticalGoals, "LEVEL1:IsPassiveOpponent", "Passive opponent",
                "INPUT:opponentProfile.lineEngagement == LOW_BLOCK",
                "INPUT:opponentProfile.playingStyle != COUNTER_ATTACK");
    }

    private static void addCompositeGoals(List<TacticalGoal> tacticalGoals) {
        addAnyRequiredGroup(tacticalGoals, "COMPOSITE:UnderdogOrCautious", "Underdog or cautious match",
                "LEVEL1:IsUnderdog",
                "LEVEL1:IsCautiousMatch");
        addAnyRequiredGroup(tacticalGoals, "COMPOSITE:NotEvenlyMatchedOrNotMustWin", "Not evenly matched or not must-win",
                "NOT:LEVEL1:IsEvenlyMatched",
                "NOT:LEVEL1:MustWin");
        addAnyRequiredGroup(tacticalGoals, "COMPOSITE:NotUnderdogOrNotMustWin", "Not underdog or not must-win",
                "NOT:LEVEL1:IsUnderdog",
                "NOT:LEVEL1:MustWin");
        addAnyRequiredGroup(tacticalGoals, "COMPOSITE:VeryAttackingOrAttacking", "Very attacking or attacking mentality",
                mentalityGoal(Mentality.VERY_ATTACKING),
                mentalityGoal(Mentality.ATTACKING));
        addAnyRequiredGroup(tacticalGoals, "COMPOSITE:CautiousOrDefensiveOrVeryDefensive", "Cautious or defensive mentality",
                mentalityGoal(Mentality.CAUTIOUS),
                mentalityGoal(Mentality.DEFENSIVE),
                mentalityGoal(Mentality.VERY_DEFENSIVE));
        addAnyRequiredGroup(tacticalGoals, "COMPOSITE:AttackingOrVeryAttacking", "Attacking or very attacking mentality",
                mentalityGoal(Mentality.ATTACKING),
                mentalityGoal(Mentality.VERY_ATTACKING));
        addAnyRequiredGroup(tacticalGoals, "COMPOSITE:DefensiveOrCautious", "Defensive or cautious mentality",
                mentalityGoal(Mentality.DEFENSIVE),
                mentalityGoal(Mentality.CAUTIOUS));
        addAnyRequiredGroup(tacticalGoals, "COMPOSITE:PositiveOrAttacking", "Positive or attacking mentality",
                mentalityGoal(Mentality.POSITIVE),
                mentalityGoal(Mentality.ATTACKING));

        addAnyRequiredGroup(tacticalGoals, "NOT:LEVEL1:IsFavorite", "Team is not a clear favorite",
                "LEVEL1:IsUnderdog",
                "LEVEL1:IsEvenlyMatched");
        addAnyRequiredGroup(tacticalGoals, "NOT:LEVEL1:IsUnderdog", "Team is not an underdog",
                "LEVEL1:IsFavorite",
                "LEVEL1:IsEvenlyMatched");
        addAnyRequiredGroup(tacticalGoals, "NOT:LEVEL1:IsEvenlyMatched", "Match is not evenly matched",
                "LEVEL1:IsFavorite",
                "LEVEL1:IsUnderdog");
        addAnyRequiredGroup(tacticalGoals, "NOT:LEVEL1:MustWin", "Match is not must-win",
                "INPUT:matchContext.competitionType == FRIENDLY",
                "INPUT:matchContext.competitionType == LEAGUE",
                "INPUT:matchContext.importance == LOW",
                "INPUT:matchContext.importance == MEDIUM");
        addAnyRequiredGroup(tacticalGoals, "NOT:LEVEL1:HasDominantPosition", "No dominant match position",
                "NOT:LEVEL1:IsFavorite",
                "INPUT:matchContext.location == AWAY");
        addAnyRequiredGroup(tacticalGoals, "NOT:LEVEL1:IsDifficultAwayMatch", "Not a difficult away match",
                "NOT:LEVEL1:IsUnderdog",
                "INPUT:matchContext.location == HOME",
                "INPUT:matchContext.importance == LOW",
                "INPUT:matchContext.importance == MEDIUM");
    }

    private static void addMentalityGoals(List<TacticalGoal> tacticalGoals) {
        addAllRequiredGroup(tacticalGoals, mentalityGoal(Mentality.ATTACKING), "Must win favorite", "LEVEL1:MustWin", "LEVEL1:IsFavorite");
        addAllRequiredGroup(tacticalGoals, mentalityGoal(Mentality.ATTACKING), "Dominant good form", "LEVEL1:HasDominantPosition", "LEVEL1:HasGoodForm");
        addAllRequiredGroup(tacticalGoals, mentalityGoal(Mentality.POSITIVE), "Must win even match", "LEVEL1:MustWin", "LEVEL1:IsEvenlyMatched");
        addAllRequiredGroup(tacticalGoals, mentalityGoal(Mentality.POSITIVE), "Dominant neutral form", "LEVEL1:HasDominantPosition", "LEVEL1:HasNeutralForm");
        addAllRequiredGroup(tacticalGoals, mentalityGoal(Mentality.POSITIVE), "Even good home match", "LEVEL1:IsEvenlyMatched", "LEVEL1:HasGoodForm", "LEVEL1:IsHomeMatch", "NOT:LEVEL1:HasDominantPosition");
        addAllRequiredGroup(tacticalGoals, mentalityGoal(Mentality.POSITIVE), "Friendly favorite", "LEVEL1:IsFavorite", "LEVEL1:IsFriendlyMatch");
        addAllRequiredGroup(tacticalGoals, mentalityGoal(Mentality.BALANCED), "Must win underdog", "LEVEL1:MustWin", "LEVEL1:IsUnderdog");
        addAllRequiredGroup(tacticalGoals, mentalityGoal(Mentality.BALANCED), "Even neutral match", "LEVEL1:IsEvenlyMatched", "LEVEL1:HasNeutralForm");
        addAllRequiredGroup(tacticalGoals, mentalityGoal(Mentality.BALANCED), "Underdog low importance", "LEVEL1:IsUnderdog", "LEVEL1:IsLowImportanceMatch");
        addAllRequiredGroup(tacticalGoals, mentalityGoal(Mentality.BALANCED), "Default mentality",
                "NOT:MENTALITY:ATTACKING",
                "NOT:MENTALITY:POSITIVE",
                "NOT:MENTALITY:DEFENSIVE",
                "NOT:MENTALITY:CAUTIOUS");
        addAllRequiredGroup(tacticalGoals, mentalityGoal(Mentality.DEFENSIVE), "Tough away match", "LEVEL1:IsDifficultAwayMatch", "COMPOSITE:NotEvenlyMatchedOrNotMustWin");
        addAllRequiredGroup(tacticalGoals, mentalityGoal(Mentality.CAUTIOUS), "Cautious but not difficult away", "LEVEL1:IsCautiousMatch", "NOT:LEVEL1:IsDifficultAwayMatch", "COMPOSITE:NotUnderdogOrNotMustWin");
        addAllRequiredGroup(tacticalGoals, mentalityGoal(Mentality.CAUTIOUS), "Can draw", "LEVEL1:CanDrawMatch");
    }

    private static void addFormationScoringGoals(List<TacticalGoal> tacticalGoals) {
        addFormationScoreGoal(tacticalGoals, "Defensive formations for underdogs or cautious matches", 2,
                new Formation[] { Formation.FORMATION_532, Formation.FORMATION_523, Formation.FORMATION_541 },
                "COMPOSITE:UnderdogOrCautious");
        addFormationScoreGoal(tacticalGoals, "Extra defensive formations for a tough away match", 1,
                new Formation[] { Formation.FORMATION_532, Formation.FORMATION_523, Formation.FORMATION_541 },
                "LEVEL1:IsDifficultAwayMatch");
        addFormationScoreGoal(tacticalGoals, "Offensive formations for favorites at home", 2,
                new Formation[] { Formation.FORMATION_343, Formation.FORMATION_352, Formation.FORMATION_3412 },
                "LEVEL1:IsFavorite", "LEVEL1:HasDominantPosition");
        addFormationScoreGoal(tacticalGoals, "Balanced 4-back formations for an even match", 2,
                new Formation[] { Formation.FORMATION_442, Formation.FORMATION_433, Formation.FORMATION_4231, Formation.FORMATION_4141, Formation.FORMATION_4312, Formation.FORMATION_451, Formation.FORMATION_4123 },
                "LEVEL1:IsEvenlyMatched");
        addFormationScoreGoal(tacticalGoals, "Wing formations for wing play", 2,
                new Formation[] { Formation.FORMATION_532, Formation.FORMATION_352, Formation.FORMATION_523, Formation.FORMATION_541, Formation.FORMATION_442, Formation.FORMATION_433 },
                "INPUT:teamProfile.attackType == WING_PLAY");
        addFormationScoreGoal(tacticalGoals, "Central formations for central play", 2,
                new Formation[] { Formation.FORMATION_433, Formation.FORMATION_4231, Formation.FORMATION_4312 },
                "INPUT:teamProfile.attackType == CENTRAL_PLAY");
        addFormationScoreGoal(tacticalGoals, "Formations with 2-3 attackers for pressing", 2,
                new Formation[] { Formation.FORMATION_442, Formation.FORMATION_433, Formation.FORMATION_451 },
                "INPUT:teamProfile.attackType == PRESSING_ATTACKERS");
        addFormationScoreGoal(tacticalGoals, "Offensive midfield formations", 1,
                new Formation[] { Formation.FORMATION_4141, Formation.FORMATION_4123, Formation.FORMATION_451 },
                "INPUT:teamProfile.midfieldQuality == CREATIVE");
        addFormationScoreGoal(tacticalGoals, "Defensive midfield formations", 1,
                new Formation[] { Formation.FORMATION_4231, Formation.FORMATION_442, Formation.FORMATION_532, Formation.FORMATION_541 },
                "INPUT:teamProfile.midfieldQuality == AGGRESSIVE");
        addFormationScoreGoal(tacticalGoals, "Balanced midfield formations", 1,
                new Formation[] { Formation.FORMATION_433, Formation.FORMATION_3412, Formation.FORMATION_523, Formation.FORMATION_532, Formation.FORMATION_442 },
                "INPUT:teamProfile.midfieldQuality == BALANCED");
        addFormationScoreGoal(tacticalGoals, "5-3-2 and 5-2-3 to counter with wing play", 2,
                new Formation[] { Formation.FORMATION_532, Formation.FORMATION_523 },
                "INPUT:teamProfile.attackType == WING_PLAY", "LEVEL1:HasCounterOpportunity");
        addFormationScoreGoal(tacticalGoals, "4-4-2 and 4-3-3 to counter with pressing attackers", 2,
                new Formation[] { Formation.FORMATION_442, Formation.FORMATION_433 },
                "INPUT:teamProfile.attackType == PRESSING_ATTACKERS", "LEVEL1:HasCounterOpportunity");
        addFormationScoreGoal(tacticalGoals, "Formations with 2 extra attackers", 1,
                new Formation[] { Formation.FORMATION_442, Formation.FORMATION_352, Formation.FORMATION_523 },
                "LEVEL1:HasAerialOpportunity");
        addFormationScoreGoal(tacticalGoals, "Simple formations for low tactical fitness", 1,
                new Formation[] { Formation.FORMATION_442, Formation.FORMATION_433 },
                "LEVEL1:HasLowFitness");
        addFormationScoreGoal(tacticalGoals, "Penalization of complex formations", -1,
                new Formation[] { Formation.FORMATION_4312, Formation.FORMATION_4123 },
                "LEVEL1:HasLowFitness");
    }

    private static void addPressingGoals(List<TacticalGoal> tacticalGoals) {
        addAllRequiredGroup(tacticalGoals, "BASE_PRESSING:HIGH", "Attacking mentality", "COMPOSITE:VeryAttackingOrAttacking");
        addAllRequiredGroup(tacticalGoals, "BASE_PRESSING:HIGH", "Positive with pressing forwards", mentalityGoal(Mentality.POSITIVE), "INPUT:teamProfile.attackType == PRESSING_ATTACKERS");
        addAllRequiredGroup(tacticalGoals, pressingGoal(PressingIntensity.HIGH), "High pressing is not reduced", "BASE_PRESSING:HIGH", "NOT:LEVEL1:IsAggressiveOpponent");
        addAllRequiredGroup(tacticalGoals, pressingGoal(PressingIntensity.MEDIUM), "Positive without pressing forwards", mentalityGoal(Mentality.POSITIVE), "INPUT:teamProfile.attackType != PRESSING_ATTACKERS");
        addAllRequiredGroup(tacticalGoals, pressingGoal(PressingIntensity.MEDIUM), "Balanced fast team", mentalityGoal(Mentality.BALANCED), "INPUT:teamProfile.physicalProfile == FAST");
        addAllRequiredGroup(tacticalGoals, pressingGoal(PressingIntensity.MEDIUM), "Reduced high pressing against aggressive opponent", "BASE_PRESSING:HIGH", "LEVEL1:IsAggressiveOpponent");
        addAllRequiredGroup(tacticalGoals, pressingGoal(PressingIntensity.MEDIUM), "Default pressing", "NOT:PRESSING:HIGH", "NOT:PRESSING:LOW");
        addAllRequiredGroup(tacticalGoals, pressingGoal(PressingIntensity.LOW), "Balanced but not fast", mentalityGoal(Mentality.BALANCED), "INPUT:teamProfile.physicalProfile != FAST");
        addAllRequiredGroup(tacticalGoals, pressingGoal(PressingIntensity.LOW), "Cautious or defensive mentality", "COMPOSITE:CautiousOrDefensiveOrVeryDefensive");
    }

    private static void addDefensiveLineGoals(List<TacticalGoal> tacticalGoals) {
        addAllRequiredGroup(tacticalGoals, defensiveLineGoal(DefensiveLineHeight.LOW), "Team cannot play a high line", "INPUT:teamProfile.highLineCapability == false");
        addAllRequiredGroup(tacticalGoals, defensiveLineGoal(DefensiveLineHeight.LOW), "Cautious or defensive mentality", "COMPOSITE:CautiousOrDefensiveOrVeryDefensive");
        addAllRequiredGroup(tacticalGoals, defensiveLineGoal(DefensiveLineHeight.HIGH), "High-line capable attacking team", "INPUT:teamProfile.highLineCapability == true", "COMPOSITE:AttackingOrVeryAttacking");
        addAllRequiredGroup(tacticalGoals, defensiveLineGoal(DefensiveLineHeight.HIGH), "Positive without counter threat", "INPUT:teamProfile.highLineCapability == true", mentalityGoal(Mentality.POSITIVE), "NOT:LEVEL1:HasCounterOpportunity");
        addAllRequiredGroup(tacticalGoals, defensiveLineGoal(DefensiveLineHeight.STANDARD), "Counter threat", "INPUT:teamProfile.highLineCapability == true", "LEVEL1:HasCounterOpportunity");
        addAllRequiredGroup(tacticalGoals, defensiveLineGoal(DefensiveLineHeight.STANDARD), "Balanced high-line capable team", "INPUT:teamProfile.highLineCapability == true", mentalityGoal(Mentality.BALANCED));
        addAllRequiredGroup(tacticalGoals, defensiveLineGoal(DefensiveLineHeight.STANDARD), "Default defensive line", "NOT:DEFENSIVE_LINE:LOW", "NOT:DEFENSIVE_LINE:HIGH");
    }

    private static void addPassingGoals(List<TacticalGoal> tacticalGoals) {
        addAllRequiredGroup(tacticalGoals, passingGoal(PassingDirectness.DIRECT), "Low tactical familiarity", "LEVEL1:HasLowFitness");
        addAllRequiredGroup(tacticalGoals, passingGoal(PassingDirectness.DIRECT), "Aggressive possession opponent", "LEVEL1:IsAggressiveOpponent", "INPUT:opponentProfile.playingStyle == POSSESSION_BASED");
        addAllRequiredGroup(tacticalGoals, passingGoal(PassingDirectness.DIRECT), "Counter opportunity with defensive mentality", "LEVEL1:HasCounterOpportunity", "COMPOSITE:DefensiveOrCautious");
        addAllRequiredGroup(tacticalGoals, passingGoal(PassingDirectness.SHORTER), "Creative midfield and good familiarity", "INPUT:teamProfile.midfieldQuality == CREATIVE", "LEVEL1:HasHighFitness", "COMPOSITE:PositiveOrAttacking");
        addAllRequiredGroup(tacticalGoals, passingGoal(PassingDirectness.SHORTER), "Passive opponent with creative midfield", "LEVEL1:IsPassiveOpponent", "INPUT:teamProfile.midfieldQuality == CREATIVE");
        addAllRequiredGroup(tacticalGoals, passingGoal(PassingDirectness.STANDARD), "Default passing", "NOT:PASSING:DIRECT", "NOT:PASSING:SHORTER");
    }

    private static void addTransitionGoals(List<TacticalGoal> tacticalGoals) {
        addAllRequiredGroup(tacticalGoals, transitionGoal(TransitionAfterLossOfBall.COUNTER_PRESS), "Balanced and fast", mentalityGoal(Mentality.BALANCED), "INPUT:teamProfile.physicalProfile == FAST");
        addAllRequiredGroup(tacticalGoals, transitionGoal(TransitionAfterLossOfBall.COUNTER_PRESS), "Attacking high press", "COMPOSITE:AttackingOrVeryAttacking", pressingGoal(PressingIntensity.HIGH));
        addAllRequiredGroup(tacticalGoals, transitionGoal(TransitionAfterLossOfBall.COUNTER_PRESS), "Pressing forwards with high press", "INPUT:teamProfile.attackType == PRESSING_ATTACKERS", pressingGoal(PressingIntensity.HIGH));
        addAllRequiredGroup(tacticalGoals, transitionGoal(TransitionAfterLossOfBall.HOLD_SHAPE), "Counter opportunity with defensive mentality", "LEVEL1:HasCounterOpportunity", "COMPOSITE:DefensiveOrCautious");
        addAllRequiredGroup(tacticalGoals, transitionGoal(TransitionAfterLossOfBall.REGROUP), "Low tactical familiarity", "LEVEL1:HasLowFitness");
        addAllRequiredGroup(tacticalGoals, transitionGoal(TransitionAfterLossOfBall.REGROUP), "Default transition", "NOT:TRANSITION:COUNTER_PRESS", "NOT:TRANSITION:HOLD_SHAPE");
    }

    private static void addFormationScoreGoal(List<TacticalGoal> tacticalGoals, String description, int points, Formation[] formations, String... conditions) {
        String level = points >= 0 ? "L2_FORMATION_SCORE_+" + points : "L2_FORMATION_SCORE_" + points;
        for (Formation formation : formations) {
            addAllRequiredGroup(tacticalGoals, formationGoal(formation), description + " (" + points + " points)", conditions, level);
        }
    }

    private static void addAllRequiredGroup(List<TacticalGoal> tacticalGoals, String goal, String description, String... conditions) {
        addAllRequiredGroup(tacticalGoals, goal, description, conditions, "L_AND_GROUP");
    }

    private static void addAllRequiredGroup(List<TacticalGoal> tacticalGoals, String goal, String description, String[] conditions, String groupLevel) {
        String group = "ALL:" + description;
        addGoal(tacticalGoals, goal, group, groupLevel, "All requirements in this group must hold: " + description + ".");
        for (String condition : conditions) {
            addGoal(tacticalGoals, group, condition, "L_CONDITION", "Condition for: " + description + ".");
        }
    }

    private static void addAnyRequiredGroup(List<TacticalGoal> tacticalGoals, String goal, String description, String... alternatives) {
        String group = "ANY:" + description;
        addGoal(tacticalGoals, goal, group, "L_OR_GROUP", "At least one alternative in this group must hold: " + description + ".");
        for (String alternative : alternatives) {
            addGoal(tacticalGoals, group, alternative, "L_CONDITION", "Alternative for: " + description + ".");
        }
    }

    private static void addGoal(List<TacticalGoal> tacticalGoals, String goal, String requirement, String level, String explanation) {
        tacticalGoals.add(new TacticalGoal(goal, requirement, level, explanation));
    }
}
