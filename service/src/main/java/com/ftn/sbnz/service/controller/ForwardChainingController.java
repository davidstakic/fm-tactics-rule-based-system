package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.dto.ForwardChainingRequest;
import com.ftn.sbnz.model.forward.TacticalRecommendation;
import com.ftn.sbnz.service.service.ForwardChainingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/forward-chaining")
public class ForwardChainingController {
    private final ForwardChainingService forwardChainingService;

    @Autowired
    public ForwardChainingController(ForwardChainingService forwardChainingService) {
        this.forwardChainingService = forwardChainingService;
    }

    @PostMapping("/recommendation")
    public TacticalRecommendation recommend(@Valid @RequestBody ForwardChainingRequest request) {
        return forwardChainingService.recommend(request.toTacticalAssistantInput());
    }
}
