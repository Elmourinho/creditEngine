package org.example.decisionengine.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record LoanRequest(
        @NotNull String personalCode,

        @NotNull
        @DecimalMin(value = "2000", message = "Amount must be at least 2000")
        @DecimalMax(value = "10000", message = "Amount must not exceed 10000")
        BigDecimal amount,

        @Min(value = 12, message = "Period must be at least 12")
        @Max(value = 60, message = "Period must not exceed 60")
        int period
) {
}
