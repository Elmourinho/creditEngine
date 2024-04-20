package org.example.decisionengine.service.impl;

import org.example.decisionengine.model.enums.PersonSegmentType;
import org.example.decisionengine.service.UserService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MockUserServiceImpl implements UserService {

    @Override
    public BigDecimal getCreditModifier(String personalCode) {
        return PersonSegmentType.getCreditModifier(personalCode);
    }
}
