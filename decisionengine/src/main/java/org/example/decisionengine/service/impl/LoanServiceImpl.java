package org.example.decisionengine.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.decisionengine.model.LoanRequest;
import org.example.decisionengine.model.LoanResponse;
import org.example.decisionengine.model.enums.LoanDecisionType;
import org.example.decisionengine.service.UserService;
import org.example.decisionengine.service.LoanService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final UserService userService;

    public static final BigDecimal MIN_LOAN_AMOUNT = BigDecimal.valueOf(2000);
    public static final BigDecimal MAX_LOAN_AMOUNT = BigDecimal.valueOf(10000);

    @Override
    public LoanResponse makeDecision(LoanRequest request) {
        var personalCode = request.personalCode();

        var modifier = userService.getCreditModifier(personalCode);
        if (modifier.compareTo(BigDecimal.ZERO) < 0) {
            return new LoanResponse(LoanDecisionType.NEGATIVE, BigDecimal.ZERO);
        }

        var possibleLoanAmount = getMaxPossibleLoanAmount(modifier, request.period());
        if (possibleLoanAmount.compareTo(MIN_LOAN_AMOUNT) < 0) {
            return new LoanResponse(LoanDecisionType.NEGATIVE, BigDecimal.ZERO);
        }
        if (possibleLoanAmount.compareTo(request.amount()) < 0) {
            return new LoanResponse(LoanDecisionType.NEGATIVE, possibleLoanAmount);
        }

        return new LoanResponse(LoanDecisionType.POSITIVE, possibleLoanAmount);
    }

    BigDecimal getMaxPossibleLoanAmount(BigDecimal modifier, int period) {
        var maxLoanAmount = modifier.multiply(BigDecimal.valueOf(period));
        return maxLoanAmount.compareTo(MAX_LOAN_AMOUNT) > 0 ? MAX_LOAN_AMOUNT : maxLoanAmount;
    }

}
