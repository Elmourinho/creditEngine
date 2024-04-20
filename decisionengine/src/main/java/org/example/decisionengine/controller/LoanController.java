package org.example.decisionengine.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.decisionengine.model.LoanRequest;
import org.example.decisionengine.model.LoanResponse;
import org.example.decisionengine.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/decision")
    public ResponseEntity<LoanResponse> makeLoanDecision(@RequestBody @Valid LoanRequest request) {
        return ResponseEntity.ok(loanService.makeDecision(request));
    }
}
