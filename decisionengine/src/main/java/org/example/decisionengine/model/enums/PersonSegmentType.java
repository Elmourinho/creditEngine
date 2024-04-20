package org.example.decisionengine.model.enums;

import lombok.Getter;
import org.example.decisionengine.error.Errors;
import org.example.decisionengine.exception.LoanException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

public enum PersonSegmentType {
    DEBT("49002010965", BigDecimal.valueOf(-1)),
    SEGMENT_1("49002010976", BigDecimal.valueOf(100)),
    SEGMENT_2("49002010987", BigDecimal.valueOf(300)),
    SEGMENT_3("49002010998", BigDecimal.valueOf(1000));

    private final String personalCode;
    @Getter
    private final BigDecimal creditModifier;

    PersonSegmentType(String personalCode, BigDecimal creditModifier) {
        this.personalCode = personalCode;
        this.creditModifier = creditModifier;
    }

    public static BigDecimal getCreditModifier(String personalCode) {
        return Arrays.stream(values())
                .filter(segment -> segment.personalCode.equals(personalCode))
                .map(segment -> segment.creditModifier)
                .findFirst()
                .orElseThrow(() -> new LoanException(Errors.CUSTOMER_NOT_FOUND, Map.of("personalCode", personalCode)));
    }
}
