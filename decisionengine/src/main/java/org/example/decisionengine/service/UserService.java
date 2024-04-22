package org.example.decisionengine.service;

import java.math.BigDecimal;

public interface UserService {

    BigDecimal getCreditModifier(String personalCode);
}
