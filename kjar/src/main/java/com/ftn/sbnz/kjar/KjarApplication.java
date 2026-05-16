package com.ftn.sbnz.kjar;

import java.io.InputStream;

import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.internal.io.ResourceFactory;
import org.kie.api.runtime.KieSession;
import org.kie.api.KieBase;
import org.kie.internal.utils.KieHelper;

import com.ftn.sbnz.model.Level1Facts;
import com.ftn.sbnz.model.Level2Facts;
import com.ftn.sbnz.model.Level3Facts;
import com.ftn.sbnz.model.TacticalAssistantInput;
import com.ftn.sbnz.model.TeamProfile;
import com.ftn.sbnz.model.OpponentProfile;
import com.ftn.sbnz.model.MatchContext;
import com.ftn.sbnz.model.RealTimeParameters;
import com.ftn.sbnz.model.BasicTacticalSettings;
import com.ftn.sbnz.model.FormationScore;

import com.ftn.sbnz.model.TeamProfile.AttackType;
import com.ftn.sbnz.model.TeamProfile.MidfieldQuality;
import com.ftn.sbnz.model.TeamProfile.PhysicalProfile;
import com.ftn.sbnz.model.OpponentProfile.PlayingStyle;
import com.ftn.sbnz.model.OpponentProfile.DefenseLineEngagement;
import com.ftn.sbnz.model.OpponentProfile.OpponentWeakness;
import com.ftn.sbnz.model.MatchContext.CompetitionType;
import com.ftn.sbnz.model.MatchContext.MatchImportance;
import com.ftn.sbnz.model.MatchContext.LocationType;
import com.ftn.sbnz.model.RealTimeParameters.MatchResult;

public class KjarApplication {

    private static final String[] RULE_FILES = {
            "rules/level1.drl",
            "rules/level3-defensive-line.drl",
            "rules/level3-passing.drl",
            "rules/level3-pressing.drl",
            "rules/level3-transition.drl",
            "rules/level2-formation-selection.drl"
    };

    public static void main(String[] args) {
        System.out.println("Starting kjar forward chaining demo...");

        String level1Rules = TemplateRuleLoader.compileTemplate(
                "/templates/level1-template.drt",
                "/templates/level1-data.xls");

        String mentalityRules = TemplateRuleLoader.compileTemplate(
                "/templates/mentality-template.drt",
                "/templates/mentality-data.xls");

        String formationRules = TemplateRuleLoader.compileTemplate(
                "/templates/formations-template.drt",
                "/templates/formations-data.xls");

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

        KieBase kieBase = kieHelper.build();
        KieSession kieSession = kieBase.newKieSession();
        try {
            insertFacts(kieSession);

            int fired = kieSession.fireAllRules();
            System.out.println("fireAllRules returned: " + fired);

            System.out.println("=== Working memory contents ===");
            kieSession.getObjects().forEach(System.out::println);
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

        input.setRealTimeParameters(new RealTimeParameters(
                30,
                MatchResult.DRAW,
                0,
                0,
                0,
                0
        ));

        Level1Facts level1Facts = new Level1Facts();

        Level2Facts level2Facts = new Level2Facts();

        Level3Facts level3Facts = new Level3Facts();

        kieSession.insert(input);
        kieSession.insert(level1Facts);
        kieSession.insert(level2Facts);
        kieSession.insert(level3Facts);

        for (BasicTacticalSettings.Formation formation : BasicTacticalSettings.Formation.values()) {
            FormationScore score = new FormationScore();
            score.setFormation(formation);
            score.setTotalScore(0);
            kieSession.insert(score);
        }
    }
}
