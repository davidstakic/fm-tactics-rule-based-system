package com.ftn.sbnz.kjar;

import java.io.InputStream;
import org.kie.api.KieBase;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

public final class BackwardChainingKieBaseFactory {
    private static final String BACKWARD_CHAINING_RULE_FILE = "/rules/backward-tactical-goals.drl";

    private BackwardChainingKieBaseFactory() {
    }

    public static KieBase create() {
        KieHelper kieHelper = new KieHelper();

        InputStream resourceStream = TacticalGoalTree.class.getResourceAsStream(BACKWARD_CHAINING_RULE_FILE);
        if (resourceStream == null) {
            throw new IllegalStateException("Could not load rule file: " + BACKWARD_CHAINING_RULE_FILE);
        }
        kieHelper.addResource(ResourceFactory.newInputStreamResource(resourceStream), ResourceType.DRL);

        Results results = kieHelper.verify();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new IllegalStateException("Backward chaining DRL build failed: "
                    + results.getMessages(Message.Level.ERROR));
        }

        return kieHelper.build();
    }
}
