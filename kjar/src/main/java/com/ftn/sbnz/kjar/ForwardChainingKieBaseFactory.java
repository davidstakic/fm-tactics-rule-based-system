package com.ftn.sbnz.kjar;

import java.io.InputStream;
import org.kie.api.KieBase;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.ResourceType;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

public final class ForwardChainingKieBaseFactory {
    private static final String[] FORWARD_CHAINING_RULE_FILES = {
            "rules/level1.drl",
            "rules/level3-defensive-line.drl",
            "rules/level3-passing.drl",
            "rules/level3-pressing.drl",
            "rules/level3-transition.drl",
            "rules/level2-formation-selection.drl",
            "cep/match-events.drl"
    };

    private ForwardChainingKieBaseFactory() {
    }

    public static KieBase create() {
        KieHelper kieHelper = new KieHelper();

        for (String ruleFile : FORWARD_CHAINING_RULE_FILES) {
            InputStream resourceStream = KjarApplication.class.getResourceAsStream("/" + ruleFile);
            if (resourceStream == null) {
                throw new IllegalStateException("Could not load rule file: " + ruleFile);
            }
            kieHelper.addResource(ResourceFactory.newInputStreamResource(resourceStream), ResourceType.DRL);
        }

        kieHelper.addContent(
                TemplateRuleLoader.compileTemplate("/templates/level1-template.drt", "/templates/level1-data.xls"),
                ResourceType.DRL);
        kieHelper.addContent(
                TemplateRuleLoader.compileTemplate("/templates/mentality-template.drt", "/templates/mentality-data.xls"),
                ResourceType.DRL);
        kieHelper.addContent(
                TemplateRuleLoader.compileTemplate("/templates/formations-template.drt", "/templates/formations-data.xls"),
                ResourceType.DRL);

        Results results = kieHelper.verify();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new IllegalStateException("Forward chaining DRL build failed: "
                    + results.getMessages(Message.Level.ERROR));
        }

        return kieHelper.build(EventProcessingOption.STREAM);
    }
}
