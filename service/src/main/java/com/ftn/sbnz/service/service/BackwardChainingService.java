package com.ftn.sbnz.service.service;

import com.ftn.sbnz.kjar.TacticalGoalTree;
import com.ftn.sbnz.model.backward.TacticalGoal;
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
            throw new IllegalArgumentException("targetGoal is required.");
        }

        KieSession kieSession = backwardChainingKieBase.newKieSession();
        try {
            for (TacticalGoal tacticalGoal : TacticalGoalTree.createTacticalGoals()) {
                kieSession.insert(tacticalGoal);
            }

            List<TacticalGoal> requirements = findRequirementsForGoal(kieSession, targetGoal.trim());
            if (requirements.isEmpty()) {
                throw new IllegalArgumentException("No backward chaining requirements found for goal: " + targetGoal);
            }

            return new BackwardChainingResponse(targetGoal.trim(), requirements);
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
}
