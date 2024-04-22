package org.example.decisionengine.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.decisionengine.model.LoanRequest;
import org.example.decisionengine.model.LoanResponse;
import org.example.decisionengine.model.enums.LoanDecisionType;
import org.example.decisionengine.service.UserService;
import org.example.decisionengine.service.LoanService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final UserService userService;

    @Value("${loan.min-amount}")
    private BigDecimal minLoanAmount;

    @Value("${loan.max-amount}")
    private BigDecimal maxLoanAmount;

    @Override
    public LoanResponse makeDecision(LoanRequest request) {
        var personalCode = request.personalCode();

        var modifier = userService.getCreditModifier(personalCode);
        if (modifier.compareTo(BigDecimal.ZERO) < 0) {
            return new LoanResponse(LoanDecisionType.NEGATIVE, BigDecimal.ZERO);
        }

        var possibleLoanAmount = getMaxPossibleLoanAmount(modifier, request.period());
        if (possibleLoanAmount.compareTo(minLoanAmount) < 0) {
            return new LoanResponse(LoanDecisionType.NEGATIVE, BigDecimal.ZERO);
        }
        if (possibleLoanAmount.compareTo(request.amount()) < 0) {
            return new LoanResponse(LoanDecisionType.NEGATIVE, possibleLoanAmount);
        }

        return new LoanResponse(LoanDecisionType.POSITIVE, possibleLoanAmount);
    }

    BigDecimal getMaxPossibleLoanAmount(BigDecimal modifier, int period) {
        var approvedAmount = modifier.multiply(BigDecimal.valueOf(period));
        return approvedAmount.compareTo(maxLoanAmount) > 0 ? maxLoanAmount : approvedAmount;
    }

}
