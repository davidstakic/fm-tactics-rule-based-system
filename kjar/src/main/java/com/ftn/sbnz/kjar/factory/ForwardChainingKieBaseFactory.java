package com.ftn.sbnz.kjar.factory;

import java.io.InputStream;

import com.ftn.sbnz.kjar.KjarApplication;
import com.ftn.sbnz.kjar.template.TemplateRuleLoader;
import org.kie.api.KieBase;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.ResourceType;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

public final class ForwardChainingKieBaseFactory {
    private static final String[] FORWARD_CHAINING_RULE_FILES = {
            "rules/forward/level1-facts.drl",
            "rules/forward/level3-defensive-line.drl",
            "rules/forward/level3-passing.drl",
            "rules/forward/level3-pressing.drl",
            "rules/forward/level3-transition.drl",
            "rules/forward/level2-formation-selection.drl",
            "rules/cep/cep.drl"
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
                TemplateRuleLoader.compileTemplate("/templates/forward/level1-facts-template.drt", "/templates/data/level1-facts-data.xls"),
                ResourceType.DRL);
        kieHelper.addContent(
                TemplateRuleLoader.compileTemplate("/templates/forward/level2-mentality-template.drt", "/templates/data/level2-mentality-data.xls"),
                ResourceType.DRL);
        kieHelper.addContent(
                TemplateRuleLoader.compileTemplate("/templates/forward/level2-formation-scoring-template.drt", "/templates/data/level2-formation-scoring-data.xls"),
                ResourceType.DRL);

        Results results = kieHelper.verify();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new IllegalStateException("Forward chaining DRL build failed: "
                    + results.getMessages(Message.Level.ERROR));
        }

        return kieHelper.build(EventProcessingOption.STREAM);
    }
}
