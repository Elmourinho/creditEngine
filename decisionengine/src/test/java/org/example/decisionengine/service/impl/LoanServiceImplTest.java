package org.example.decisionengine.service.impl;

import org.example.decisionengine.model.LoanRequest;
import org.example.decisionengine.model.enums.LoanDecisionType;
import org.example.decisionengine.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
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

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {

        Field fieldMaxAmount = LoanServiceImpl.class.getDeclaredField("maxLoanAmount");
        fieldMaxAmount.setAccessible(true);
        fieldMaxAmount.set(loanService, BigDecimal.valueOf(10000));

        Field fieldMinAmount = LoanServiceImpl.class.getDeclaredField("minLoanAmount");
        fieldMinAmount.setAccessible(true);
        fieldMinAmount.set(loanService, BigDecimal.valueOf(2000));
    }

    @Test
    void makeDecision_shouldReturnNegativeAndZero_WhenCustomerHasDebt() {
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
    void makeDecision_shouldReturnNegativeAndZero_WhenApprovedAmountIsLessThanMinimum() {
        //given
        var request = new LoanRequest(PERSONAL_CODE, BigDecimal.valueOf(5000), 20);

        //when
        when(userService.getCreditModifier(PERSONAL_CODE)).thenReturn(BigDecimal.valueOf(50));

        var response = loanService.makeDecision(request);

        //then
        assertEquals(LoanDecisionType.NEGATIVE, response.decisionType());
        assertEquals(BigDecimal.ZERO, response.approvedAmount());
        verify(userService).getCreditModifier(PERSONAL_CODE);
    }

    @Test
    void makeDecision_shouldReturnPositiveAndApprovedAmount_WhenApprovedAmountIsGreaterThanRequested() {
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
    void makeDecision_shouldReturnNegativeAndApprovedAmount_WhenApprovedAmountIsLessThanRequested() {
        //given
        var request = new LoanRequest(PERSONAL_CODE, BigDecimal.valueOf(8000), 40);
        var modifier = BigDecimal.valueOf(100);
        var maxPossibleLoanAmount = BigDecimal.valueOf(4000);

        //when
        when(userService.getCreditModifier(PERSONAL_CODE)).thenReturn(modifier);

        var response = loanService.makeDecision(request);

        //then
        assertEquals(LoanDecisionType.NEGATIVE, response.decisionType());
        assertEquals(maxPossibleLoanAmount, response.approvedAmount());
        verify(userService).getCreditModifier(PERSONAL_CODE);
    }

    @Test
    void makeDecision_shouldReturnPositiveAndMaxAmount_WhenApprovedAmountIsGreaterThanMax() {
        //given
        var request = new LoanRequest(PERSONAL_CODE, BigDecimal.valueOf(8000), 50);
        var modifier = BigDecimal.valueOf(3000);
        var maxPossibleLoanAmount = BigDecimal.valueOf(10000);

        //when
        when(userService.getCreditModifier(PERSONAL_CODE)).thenReturn(modifier);

        var response = loanService.makeDecision(request);

        //then
        assertEquals(LoanDecisionType.POSITIVE, response.decisionType());
        assertEquals(maxPossibleLoanAmount, response.approvedAmount());
        verify(userService).getCreditModifier(PERSONAL_CODE);
    }

}