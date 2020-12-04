package com.example.urlshortener.service;

import com.example.urlshortener.controller.dto.CreateRateLimitRulesRequest;

public interface RateLimitService {

    String createRateLimitRules(CreateRateLimitRulesRequest request);

    void createAccessLog(String ip);

    boolean validateRateLimit(String ip);

}
