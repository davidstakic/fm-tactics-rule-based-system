package com.ftn.sbnz.kjar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.api.runtime.rule.Variable;

import com.ftn.sbnz.model.backward.TacticalGoal;

public final class TacticalGoalPrinter {

    private TacticalGoalPrinter() {
    }

    public static void printGoalRequirements(KieSession kieSession, String targetGoal) {
        System.out.println("=== Backward chaining explanation for " + targetGoal + " ===");
        for (TacticalGoal tacticalGoal : findRequirementsForGoal(kieSession, targetGoal)) {
            System.out.println(
                    tacticalGoal.getLevel()
                            + " | "
                            + tacticalGoal.getRequirement()
                            + " | "
                            + tacticalGoal.getExplanation());
        }
    }

    private static List<TacticalGoal> findRequirementsForGoal(KieSession kieSession, String targetGoal) {
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
}

