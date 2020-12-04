package com.example.urlshortener.controller;

import com.example.urlshortener.controller.dto.CreateRateLimitRulesRequest;
import com.example.urlshortener.service.RateLimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RateLimitController {

    private final RateLimitService rateLimitService;

    @PostMapping(value = "/rate-limit-rules")
    public String createRateLimitRules(@RequestBody @Valid CreateRateLimitRulesRequest request) {
        return rateLimitService.createRateLimitRules(request);
    }

}
