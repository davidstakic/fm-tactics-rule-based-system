package com.ftn.sbnz.service.service;

import com.ftn.sbnz.model.cep.CEPRecommendation;
import com.ftn.sbnz.model.cep.MatchStateEvent;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CEPService {
    private final TacticalSessionService tacticalSessionService;

    @Autowired
    public CEPService(TacticalSessionService tacticalSessionService) {
        this.tacticalSessionService = tacticalSessionService;
    }

    public List<CEPRecommendation> startMatch() {
        return tacticalSessionService.startMatch();
    }

    public List<CEPRecommendation> processMatchState(MatchStateEvent matchStateEvent) {
        return tacticalSessionService.insertMatchState(matchStateEvent);
    }
}
