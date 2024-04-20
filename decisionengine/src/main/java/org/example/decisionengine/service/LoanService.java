package org.example.decisionengine.service;

import org.example.decisionengine.model.LoanRequest;
import org.example.decisionengine.model.LoanResponse;

public interface LoanService {

    LoanResponse makeDecision (LoanRequest request);
}
