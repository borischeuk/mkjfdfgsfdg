package com.example.urlshortener.service;

import com.example.urlshortener.controller.dto.CreateRateLimitRulesRequest;
import com.example.urlshortener.controller.dto.RateLimitRule;
import com.example.urlshortener.repository.AccessLogRepository;
import com.example.urlshortener.repository.RateLimitRuleRepository;
import com.example.urlshortener.repository.entity.AccessLogEntity;
import com.example.urlshortener.repository.entity.RateLimitRuleEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultRateLimitService implements RateLimitService {

    private final RateLimitRuleRepository rateLimitRuleRepository;
    private final AccessLogRepository accessLogRepository;

    public String createRateLimitRules(CreateRateLimitRulesRequest request) {

        List<RateLimitRuleEntity> rateLimitRuleEntityList = request.getRules().stream()
                .map(this::convertToRateLimitRuleEntity)
                .collect(Collectors.toList());

        rateLimitRuleRepository.saveAll(rateLimitRuleEntityList);

        return "";
    }

    public void createAccessLog(String ip) {

        AccessLogEntity accessLogEntity = AccessLogEntity.builder()
                .id(UUID.randomUUID().toString())
                .ip(ip)
                .build();

        accessLogRepository.save(accessLogEntity);

    }

    public boolean validateRateLimit(String ip) {

        this.createAccessLog(ip);

        RateLimitRuleEntity rateLimitRuleEntity = rateLimitRuleRepository.findById(ip).orElse(null);
        if (rateLimitRuleEntity == null) {
            return true;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lowBound = now.minusSeconds(rateLimitRuleEntity.getTimeInterval() / 1000);

        Integer count = accessLogRepository.getAccessLogByTimeRange(ip, lowBound, now);
        if (count > rateLimitRuleEntity.getRateLimit()) {
            return false;
        }

        return true;
    }

    private RateLimitRuleEntity convertToRateLimitRuleEntity(RateLimitRule rateLimitRule) {
        return RateLimitRuleEntity.builder()
                .ip(rateLimitRule.getIp())
                .rateLimit(rateLimitRule.getLimit())
                .timeInterval(rateLimitRule.getTime())
                .build();
    }

}
