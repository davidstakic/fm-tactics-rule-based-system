package com.ftn.sbnz.kjar;

import java.io.InputStream;

import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.ResourceType;
import org.kie.internal.io.ResourceFactory;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.api.KieBase;
import org.kie.internal.utils.KieHelper;

import com.ftn.sbnz.kjar.backward.TacticalGoalPrinter;
import com.ftn.sbnz.kjar.backward.TacticalGoalTree;
import com.ftn.sbnz.kjar.template.TemplateRuleLoader;
import com.ftn.sbnz.model.backward.TacticalGoal;
import com.ftn.sbnz.model.cep.MatchStateEvent;
import com.ftn.sbnz.model.enums.AttackType;
import com.ftn.sbnz.model.enums.CompetitionType;
import com.ftn.sbnz.model.enums.DefenseLineEngagement;
import com.ftn.sbnz.model.enums.Formation;
import com.ftn.sbnz.model.enums.LocationType;
import com.ftn.sbnz.model.enums.MatchImportance;
import com.ftn.sbnz.model.enums.MatchResult;
import com.ftn.sbnz.model.enums.Mentality;
import com.ftn.sbnz.model.enums.MidfieldQuality;
import com.ftn.sbnz.model.enums.OpponentWeakness;
import com.ftn.sbnz.model.enums.PhysicalProfile;
import com.ftn.sbnz.model.enums.PlayingStyle;
import com.ftn.sbnz.model.enums.PressingIntensity;
import com.ftn.sbnz.model.forward.FormationScore;
import com.ftn.sbnz.model.forward.Level1Facts;
import com.ftn.sbnz.model.forward.Level2Facts;
import com.ftn.sbnz.model.forward.Level3Facts;
import com.ftn.sbnz.model.forward.MatchContext;
import com.ftn.sbnz.model.forward.OpponentProfile;
import com.ftn.sbnz.model.forward.TacticalAssistantInput;
import com.ftn.sbnz.model.forward.TeamProfile;

public class KjarApplication {

    private static final String[] RULE_FILES = {
            "rules/forward/level1-facts.drl",
            "rules/forward/level3-defensive-line.drl",
            "rules/forward/level3-passing.drl",
            "rules/forward/level3-pressing.drl",
            "rules/forward/level3-transition.drl",
            "rules/forward/level2-formation-selection.drl",
            "rules/backward/backward-chaining.drl",
            "rules/cep/cep.drl"
    };

    public static void main(String[] args) {
        System.out.println("Starting kjar forward chaining demo...");

        String level1Rules = TemplateRuleLoader.compileTemplate(
                "/templates/forward/level1-facts-template.drt",
                "/templates/data/level1-facts-data.xls");

        String mentalityRules = TemplateRuleLoader.compileTemplate(
                "/templates/forward/level2-mentality-template.drt",
                "/templates/data/level2-mentality-data.xls");

        String formationRules = TemplateRuleLoader.compileTemplate(
                "/templates/forward/level2-formation-scoring-template.drt",
                "/templates/data/level2-formation-scoring-data.xls");

        KieHelper kieHelper = new KieHelper();

        for (String ruleFile : RULE_FILES) {
            InputStream resourceStream = KjarApplication.class.getResourceAsStream("/" + ruleFile);
            if (resourceStream == null) {
                throw new IllegalStateException("Could not load rule file: " + ruleFile);
            }
            kieHelper.addResource(ResourceFactory.newInputStreamResource(resourceStream), ResourceType.DRL);
        }

        kieHelper.addContent(level1Rules, ResourceType.DRL);
        kieHelper.addContent(mentalityRules, ResourceType.DRL);
        kieHelper.addContent(formationRules, ResourceType.DRL);

        Results results = kieHelper.verify();
        if (results.hasMessages(Message.Level.ERROR)) {
            results.getMessages(Message.Level.ERROR).forEach(System.err::println);
            throw new IllegalStateException("DRL build failed due to errors");
        }

        KieBase kieBase = kieHelper.build(EventProcessingOption.STREAM);
        KieSession kieSession = kieBase.newKieSession();
        try {
            insertFacts(kieSession);
            insertTacticalGoals(kieSession);

            int fired = kieSession.fireAllRules();
            System.out.println("initial fireAllRules returned: " + fired);

            TacticalGoalPrinter.printGoalRequirements(
                    kieSession,
                    TacticalGoalTree.formationGoal(Formation.FORMATION_433));
            TacticalGoalPrinter.printGoalRequirements(
                    kieSession,
                    TacticalGoalTree.mentalityGoal(Mentality.ATTACKING));
            TacticalGoalPrinter.printGoalRequirements(
                    kieSession,
                    TacticalGoalTree.pressingGoal(PressingIntensity.HIGH));

            insertCepEvents(kieSession);

            int cepFired = kieSession.fireAllRules();
            System.out.println("CEP fireAllRules returned: " + cepFired);

            System.out.println("=== Working memory contents ===");
            kieSession.getObjects().stream()
                    .filter(object -> !(object instanceof TacticalGoal))
                    .forEach(System.out::println);
        } finally {
            if (kieSession != null) {
                kieSession.dispose();
            }
        }
    }

    private static void insertFacts(KieSession kieSession) {
        TacticalAssistantInput input = new TacticalAssistantInput();
        input.setTeamProfile(new TeamProfile(
                0.85,
                "W-W-D-W-W",
                90,
                PhysicalProfile.FAST,
                MidfieldQuality.CREATIVE,
                true,
                AttackType.PRESSING_ATTACKERS
        ));

        input.setOpponentProfile(new OpponentProfile(
                0.80,
                PlayingStyle.POSSESSION_BASED,
                DefenseLineEngagement.MID_BLOCK,
                OpponentWeakness.SLOW_DEFENDERS
        ));

        input.setMatchContext(new MatchContext(
                CompetitionType.LEAGUE,
                MatchImportance.HIGH,
                LocationType.HOME
        ));

        Level1Facts level1Facts = new Level1Facts();

        Level2Facts level2Facts = new Level2Facts();

        Level3Facts level3Facts = new Level3Facts();

        kieSession.insert(input);
        kieSession.insert(level1Facts);
        kieSession.insert(level2Facts);
        kieSession.insert(level3Facts);

        for (Formation formation : Formation.values()) {
            FormationScore score = new FormationScore();
            score.setFormation(formation);
            score.setTotalScore(0);
            kieSession.insert(score);
        }
    }

    private static void insertTacticalGoals(KieSession kieSession) {
        for (TacticalGoal tacticalGoal : TacticalGoalTree.createTacticalGoals()) {
            kieSession.insert(tacticalGoal);
        }
    }

    private static void insertCepEvents(KieSession kieSession) {
        EntryPoint matchEvents = kieSession.getEntryPoint("match-events");
        long baseTimestamp = System.currentTimeMillis();

        matchEvents.insert(new MatchStateEvent(
                baseTimestamp,
                0,
                MatchResult.DRAW,
                0,
                0));

        matchEvents.insert(new MatchStateEvent(
                baseTimestamp + 60_000,
                68,
                MatchResult.DRAW,
                0,
                0));

        matchEvents.insert(new MatchStateEvent(
                baseTimestamp + 120_000,
                72,
                MatchResult.LOSING,
                0,
                0));

        matchEvents.insert(new MatchStateEvent(
                baseTimestamp + 180_000,
                76,
                MatchResult.WINNING,
                0,
                1));

        matchEvents.insert(new MatchStateEvent(
                baseTimestamp + 240_000,
                80,
                MatchResult.WINNING,
                0,
                2));
    }

}
