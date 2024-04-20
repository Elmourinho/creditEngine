package org.example.decisionengine.service;

import java.math.BigDecimal;

public interface UserService {

//    boolean hasDebt(String personalCode);

//    Optional<PersonSegmentType> getSegmentType(String personalCode);
    BigDecimal getCreditModifier(String personalCode);
}
