package org.example.decisionengine.model;

import org.example.decisionengine.model.enums.LoanDecisionType;

import java.math.BigDecimal;

public record LoanResponse(
        LoanDecisionType decisionType,
        BigDecimal approvedAmount
) {
}
