package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.dto.BackwardChainingRequest;
import com.ftn.sbnz.model.dto.BackwardChainingResponse;
import com.ftn.sbnz.service.service.BackwardChainingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/backward-chaining")
public class BackwardChainingController {
    private final BackwardChainingService backwardChainingService;

    @Autowired
    public BackwardChainingController(BackwardChainingService backwardChainingService) {
        this.backwardChainingService = backwardChainingService;
    }

    @PostMapping("/requirements")
    public BackwardChainingResponse explain(@Valid @RequestBody BackwardChainingRequest request) {
        return backwardChainingService.explain(request.resolveTargetGoal());
    }
}
