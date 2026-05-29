package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.cep.CEPRecommendation;
import com.ftn.sbnz.model.cep.MatchStateEvent;
import com.ftn.sbnz.model.enums.Formation;
import com.ftn.sbnz.model.enums.MatchResult;
import com.ftn.sbnz.model.forward.BasicTacticalSettings;
import com.ftn.sbnz.model.forward.FormationScore;
import com.ftn.sbnz.model.forward.KeyTeamInstructions;
import com.ftn.sbnz.model.forward.Level1Facts;
import com.ftn.sbnz.model.forward.Level2Facts;
import com.ftn.sbnz.model.forward.Level3Facts;
import com.ftn.sbnz.model.forward.TacticalAssistantInput;
import com.ftn.sbnz.model.forward.TacticalExplanationStep;
import com.ftn.sbnz.model.forward.TacticalRecommendation;
import com.ftn.sbnz.model.forward.factory.TacticalExplanationFactory;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.kie.api.KieBase;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TacticalSessionService {
    private final KieBase forwardChainingKieBase;
    private KieSession activeSession;
    private boolean matchStarted;

    @Autowired
    public TacticalSessionService(@Qualifier("forwardChainingKieBase") KieBase forwardChainingKieBase) {
        this.forwardChainingKieBase = forwardChainingKieBase;
    }

    public synchronized TacticalRecommendation runForwardChaining(TacticalAssistantInput input) {
        disposeActiveSession();

        KieSession kieSession = forwardChainingKieBase.newKieSession();
        Level2Facts level2Facts = new Level2Facts();
        Level3Facts level3Facts = new Level3Facts();

        kieSession.insert(input);
        kieSession.insert(new Level1Facts());
        kieSession.insert(level2Facts);
        kieSession.insert(level3Facts);

        for (Formation formation : Formation.values()) {
            kieSession.insert(new FormationScore(formation));
        }

        kieSession.fireAllRules();

        activeSession = kieSession;
        matchStarted = false;

        return toRecommendation(level2Facts, level3Facts, selectedFormationScore(kieSession, level2Facts.getSelectedFormation()));
    }

    public synchronized List<CEPRecommendation> startMatch() {
        KieSession kieSession = requireActiveSession();
        if (matchStarted) {
            throw new IllegalArgumentException("The match has already been started.");
        }
        matchStarted = true;
        return insertMatchState(kieSession, new MatchStateEvent(
                System.currentTimeMillis(),
                0,
                MatchResult.DRAW,
                0,
                0));
    }

    public synchronized List<CEPRecommendation> insertMatchState(MatchStateEvent matchStateEvent) {
        if (!matchStarted) {
            throw new IllegalArgumentException("Start the match before sending a match update.");
        }
        return insertMatchState(requireActiveSession(), matchStateEvent);
    }

    private List<CEPRecommendation> insertMatchState(KieSession kieSession, MatchStateEvent matchStateEvent) {
        Set<String> existingRecommendationTypes = currentRecommendationTypes(kieSession);

        EntryPoint matchEvents = kieSession.getEntryPoint("match-events");
        matchEvents.insert(matchStateEvent);
        kieSession.fireAllRules();

        List<CEPRecommendation> newRecommendations = new ArrayList<>();
        for (Object object : kieSession.getObjects(new ClassObjectFilter(CEPRecommendation.class))) {
            CEPRecommendation recommendation = (CEPRecommendation) object;
            if (!existingRecommendationTypes.contains(recommendation.getEventType())) {
                newRecommendations.add(recommendation);
            }
        }
        return newRecommendations;
    }

    private Set<String> currentRecommendationTypes(KieSession kieSession) {
        Set<String> recommendationTypes = new LinkedHashSet<>();
        for (Object object : kieSession.getObjects(new ClassObjectFilter(CEPRecommendation.class))) {
            recommendationTypes.add(((CEPRecommendation) object).getEventType());
        }
        return recommendationTypes;
    }

    private KieSession requireActiveSession() {
        if (activeSession == null) {
            throw new IllegalArgumentException("Generate a tactical recommendation before starting the match.");
        }
        return activeSession;
    }

    private void disposeActiveSession() {
        if (activeSession != null) {
            activeSession.dispose();
            activeSession = null;
            matchStarted = false;
        }
    }

    private FormationScore selectedFormationScore(KieSession kieSession, Formation selectedFormation) {
        if (selectedFormation == null) {
            return null;
        }
        for (Object object : kieSession.getObjects(new ClassObjectFilter(FormationScore.class))) {
            FormationScore score = (FormationScore) object;
            if (selectedFormation.equals(score.getFormation())) {
                return score;
            }
        }
        return null;
    }

    private TacticalRecommendation toRecommendation(Level2Facts level2Facts, Level3Facts level3Facts,
            FormationScore selectedFormationScore) {
        TacticalRecommendation recommendation = new TacticalRecommendation();
        recommendation.setBasicSettings(new BasicTacticalSettings(
                level2Facts.getSelectedFormation(),
                level2Facts.getSelectedMentality()));
        recommendation.setTeamInstructions(new KeyTeamInstructions(
                level3Facts.getPassingDirectness(),
                level3Facts.getPressingIntensity(),
                level3Facts.getDefensiveLineHeight(),
                level3Facts.getTransitionAfterLossOfBall()));

        if (level2Facts.getSelectedFormation() != null) {
            recommendation.addExplanationStep(TacticalExplanationFactory.formation(
                    level2Facts.getSelectedFormation(),
                    selectedFormationScore != null ? selectedFormationScore.getScoreBreakdown() : null));
        }
        for (TacticalExplanationStep step : level2Facts.getExplanationSteps()) {
            recommendation.addExplanationStep(step);
        }
        for (TacticalExplanationStep step : level3Facts.getExplanationSteps()) {
            recommendation.addExplanationStep(step);
        }
        collectTradeoffs(recommendation);
        return recommendation;
    }

    private void collectTradeoffs(TacticalRecommendation recommendation) {
        Map<String, TacticalExplanationStep> finalStepByDecision = new LinkedHashMap<>();
        for (TacticalExplanationStep step : recommendation.getExplanationSteps()) {
            finalStepByDecision.put(step.getDecision(), step);
        }

        for (TacticalExplanationStep step : finalStepByDecision.values()) {
            step.addTradeoffsTo(recommendation);
        }
    }
}
