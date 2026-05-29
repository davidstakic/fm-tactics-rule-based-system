package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.cep.CEPRecommendation;
import com.ftn.sbnz.model.dto.CEPMatchStateRequest;
import com.ftn.sbnz.service.service.CEPService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cep")
public class CEPController {
    private final CEPService cepService;

    @Autowired
    public CEPController(CEPService cepService) {
        this.cepService = cepService;
    }

    @PostMapping("/match/start")
    public List<CEPRecommendation> startMatch() {
        return cepService.startMatch();
    }

    @PostMapping("/match/state")
    public List<CEPRecommendation> processMatchState(@Valid @RequestBody CEPMatchStateRequest request) {
        return cepService.processMatchState(request.toMatchStateEvent());
    }
}
