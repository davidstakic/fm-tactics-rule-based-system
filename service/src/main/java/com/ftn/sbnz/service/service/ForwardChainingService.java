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
        validateInput(input);
        return tacticalSessionService.runForwardChaining(input);
    }

    private void validateInput(TacticalAssistantInput input) {
        if (input == null) {
            throw new IllegalArgumentException("Tactical input is required.");
        }
        if (input.getTeamProfile() == null) {
            throw new IllegalArgumentException("teamProfile is required.");
        }
        if (input.getOpponentProfile() == null) {
            throw new IllegalArgumentException("opponentProfile is required.");
        }
        if (input.getMatchContext() == null) {
            throw new IllegalArgumentException("matchContext is required.");
        }
    }
}
