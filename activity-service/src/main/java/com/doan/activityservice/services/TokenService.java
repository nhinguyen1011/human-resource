package com.doan.activityservice.services;

import com.doan.activityservice.models.StravaToken;
import com.doan.activityservice.repositories.StravaTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private StravaTokenRepository tokenRepository;

    public StravaToken saveToken(StravaToken token) {
        return tokenRepository.save(token);
    }

    public StravaToken getTokenForEmployee(String employeeId) {
        return tokenRepository.findByEmployeeId(employeeId);
    }
}
