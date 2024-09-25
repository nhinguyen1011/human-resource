package com.doan.activityservice.services;

import com.doan.activityservice.models.Activity;
import com.doan.activityservice.models.StravaToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

@Service
public class StravaService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TokenService tokenService;

    @Value("${strava.client.id}")
    private String clientId;

    @Value("${strava.client.secret}")
    private String clientSecret;

    @Value("${strava.redirect.uri}")
    private String redirectUri;

    public String getAuthorizationUrl(String employeeId) {
        return String.format("https://www.strava.com/oauth/authorize?client_id=%s&response_type=code&redirect_uri=%s&approval_prompt=force&scope=activity:read_all&state=%s",
                clientId, redirectUri, employeeId);
    }

    public StravaToken exchangeCodeForToken(String code, String employeeId) {
        String tokenUrl = "https://www.strava.com/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = String.format("client_id=%s&client_secret=%s&code=%s&grant_type=authorization_code",
                clientId, clientSecret, code);

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<StravaToken> response = restTemplate.postForEntity(tokenUrl, request, StravaToken.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                StravaToken token = response.getBody();
                assert token != null;
                token.setEmployeeId(employeeId);
                tokenService.saveToken(token);
                return token;

            } catch (Exception e) {

                throw e; // hoặc xử lý lỗi phù hợp
            }
        }
        return null;
    }

    public List<?> fetchActivities(String employeeId, LocalDateTime start, LocalDateTime end) {
        StravaToken token = tokenService.getTokenForEmployee(employeeId);
        if (token == null) {
            throw new RuntimeException("User has not authenticated with Strava. Please connect your Strava account first.");
        }
        if (token.getExpiresAt() < System.currentTimeMillis() / 1000) {
            token = refreshToken(token);
        }

        String url = String.format("https://www.strava.com/api/v3/athlete/activities?after=%d&before=%d",
                start.toEpochSecond(ZoneOffset.UTC), end.toEpochSecond(ZoneOffset.UTC));

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.getAccessToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Activity[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Activity[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return Arrays.asList(response.getBody());
        }

        throw new RuntimeException("Failed to fetch activities from Strava");
    }

    private StravaToken refreshToken(StravaToken token) {
        String refreshUrl = "https://www.strava.com/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = String.format("client_id=%s&client_secret=%s&refresh_token=%s&grant_type=refresh_token",
                clientId, clientSecret, token.getRefreshToken());

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<StravaToken> response = restTemplate.postForEntity(refreshUrl, request, StravaToken.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            StravaToken newToken = response.getBody();
            assert newToken != null;
            newToken.setEmployeeId(token.getEmployeeId());
            return tokenService.saveToken(newToken);
        }

        throw new RuntimeException("Failed to refresh token");
    }
}
