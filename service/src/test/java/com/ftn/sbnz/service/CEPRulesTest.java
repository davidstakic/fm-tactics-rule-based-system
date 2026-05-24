package com.ftn.sbnz.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ftn.sbnz.kjar.factory.ForwardChainingKieBaseFactory;
import com.ftn.sbnz.model.cep.CEPRecommendation;
import com.ftn.sbnz.model.cep.MatchStateEvent;
import com.ftn.sbnz.model.enums.MatchResult;
import com.ftn.sbnz.model.enums.Mentality;
import com.ftn.sbnz.model.enums.PassingDirectness;
import com.ftn.sbnz.model.enums.PressingIntensity;
import com.ftn.sbnz.model.enums.TransitionAfterLossOfBall;
import com.ftn.sbnz.model.forward.Level2Facts;
import java.util.Collection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

class CEPRulesTest {

    private KieSession kieSession;
    private EntryPoint matchEvents;
    private long baseTimestamp;

    @BeforeEach
    void setUp() {
        kieSession = ForwardChainingKieBaseFactory.create().newKieSession();
        kieSession.insert(new Level2Facts(null, Mentality.BALANCED, null));
        matchEvents = kieSession.getEntryPoint("match-events");
        baseTimestamp = System.currentTimeMillis();
    }

    @AfterEach
    void tearDown() {
        kieSession.dispose();
    }

    @Test
    void opponentRedCardCreatesAttackingRecommendation() {
        insertMatchState(0L, 10, MatchResult.DRAW, 0, 0);
        kieSession.fireAllRules();

        insertMatchState(60_000L, 11, MatchResult.DRAW, 0, 1);
        kieSession.fireAllRules();

        CEPRecommendation recommendation = findRecommendation("OPPONENT_RED_CARD_1");

        assertNotNull(recommendation);
        assertEquals(Mentality.POSITIVE, recommendation.getAdjustedMentality());
        assertEquals(PassingDirectness.SHORTER, recommendation.getAdjustedPassing());
        assertEquals(PressingIntensity.HIGH, recommendation.getAdjustedPressing());
    }

    @Test
    void ownTeamRedCardCreatesRiskReducingRecommendation() {
        insertMatchState(0L, 30, MatchResult.DRAW, 0, 0);
        kieSession.fireAllRules();

        insertMatchState(60_000L, 31, MatchResult.DRAW, 1, 0);
        kieSession.fireAllRules();

        CEPRecommendation recommendation = findRecommendation("OWN_TEAM_RED_CARD_1");

        assertNotNull(recommendation);
        assertEquals(Mentality.DEFENSIVE, recommendation.getAdjustedMentality());
        assertEquals(PressingIntensity.LOW, recommendation.getAdjustedPressing());
        assertEquals(TransitionAfterLossOfBall.REGROUP, recommendation.getAdjustedTransition());
    }

    @Test
    void losingLateCreatesUrgentAttackingRecommendation() {
        insertMatchState(0L, 69, MatchResult.DRAW, 0, 0);
        kieSession.fireAllRules();

        insertMatchState(60_000L, 70, MatchResult.LOSING, 0, 0);
        kieSession.fireAllRules();

        CEPRecommendation recommendation = findRecommendation("LOSING_LATE");

        assertNotNull(recommendation);
        assertEquals(Mentality.ATTACKING, recommendation.getAdjustedMentality());
        assertEquals(PressingIntensity.HIGH, recommendation.getAdjustedPressing());
        assertEquals(PassingDirectness.DIRECT, recommendation.getAdjustedPassing());
    }

    @Test
    void winningInClosingPhaseCreatesLeadProtectionRecommendation() {
        insertMatchState(0L, 74, MatchResult.DRAW, 0, 0);
        kieSession.fireAllRules();

        insertMatchState(60_000L, 75, MatchResult.WINNING, 0, 0);
        kieSession.fireAllRules();

        CEPRecommendation recommendation = findRecommendation("WINNING_CLOSING_PHASE");

        assertNotNull(recommendation);
        assertEquals(Mentality.DEFENSIVE, recommendation.getAdjustedMentality());
        assertEquals(PressingIntensity.LOW, recommendation.getAdjustedPressing());
        assertEquals(TransitionAfterLossOfBall.HOLD_SHAPE, recommendation.getAdjustedTransition());
    }

    private void insertMatchState(Long timestamp, Integer minute, MatchResult result,
            Integer ownTeamRedCards, Integer opponentRedCards) {
        matchEvents.insert(new MatchStateEvent(baseTimestamp + timestamp, minute, result,
                ownTeamRedCards, opponentRedCards));
    }

    private CEPRecommendation findRecommendation(String eventType) {
        Collection<?> recommendations = kieSession.getObjects(new ClassObjectFilter(CEPRecommendation.class));
        return recommendations.stream()
                .map(CEPRecommendation.class::cast)
                .filter(recommendation -> eventType.equals(recommendation.getEventType()))
                .findFirst()
                .orElse(null);
    }
}
