package org.example.decisionengine.service.impl;

import org.example.decisionengine.model.LoanRequest;
import org.example.decisionengine.model.enums.LoanDecisionType;
import org.example.decisionengine.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private LoanServiceImpl loanService;

    private static final String PERSONAL_CODE = "12345";

    @Test
    void testMakeDecision_WithNegativeModifier_ReturnsNegativeResponse() {
        //given
        var request = new LoanRequest(PERSONAL_CODE, BigDecimal.valueOf(5000), 36);

        //when
        when(userService.getCreditModifier(PERSONAL_CODE)).thenReturn(BigDecimal.valueOf(-100));

        var response = loanService.makeDecision(request);

        //then
        assertEquals(LoanDecisionType.NEGATIVE, response.decisionType());
        assertEquals(BigDecimal.ZERO, response.approvedAmount());
        verify(userService).getCreditModifier(PERSONAL_CODE);
    }

    @Test
    void testMakeDecision_WithValidRequest_ReturnsPositiveResponse() {
        //given
        var request = new LoanRequest(PERSONAL_CODE, BigDecimal.valueOf(5000), 36);
        var modifier = BigDecimal.valueOf(200);
        var maxPossibleLoanAmount = BigDecimal.valueOf(7200);

        //when
        when(userService.getCreditModifier(PERSONAL_CODE)).thenReturn(modifier);

        var response = loanService.makeDecision(request);

        //then
        assertEquals(LoanDecisionType.POSITIVE, response.decisionType());
        assertEquals(maxPossibleLoanAmount, response.approvedAmount());
        verify(userService).getCreditModifier(PERSONAL_CODE);
    }

    @Test
    void testGetMaxPossibleLoanAmount_WithLargeModifier_ReturnsMaxAmount() {
        //given
        var modifier = BigDecimal.valueOf(10000);
        var period = 12;

        //when
        var maxPossibleLoanAmount = loanService.getMaxPossibleLoanAmount(modifier, period);

        //then
        assertEquals(LoanServiceImpl.MAX_LOAN_AMOUNT, maxPossibleLoanAmount);
    }

    @Test
    void testGetMaxPossibleLoanAmount_WithSmallModifier_ReturnsModifiedAmount() {
        //given
        var modifier = BigDecimal.valueOf(100);
        var period = 60;
        var expectedMaxLoanAmount = modifier.multiply(BigDecimal.valueOf(period));

        //when
        var maxPossibleLoanAmount = loanService.getMaxPossibleLoanAmount(modifier, period);

        //then
        assertEquals(expectedMaxLoanAmount, maxPossibleLoanAmount);
    }


}