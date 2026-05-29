package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.forward.TacticalAssistantInput;
import com.ftn.sbnz.model.forward.TacticalRecommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForwardChainingService {
    private final TacticalSessionService tacticalSessionService;

    @Autowired
    public ForwardChainingService(TacticalSessionService tacticalSessionService) {
        this.tacticalSessionService = tacticalSessionService;
    }

    public TacticalRecommendation recommend(TacticalAssistantInput input) {
        return tacticalSessionService.runForwardChaining(input);
    }
}
