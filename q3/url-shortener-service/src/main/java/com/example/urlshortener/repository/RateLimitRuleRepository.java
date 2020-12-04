package com.example.urlshortener.repository;

import com.example.urlshortener.repository.entity.RateLimitRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateLimitRuleRepository extends JpaRepository<RateLimitRuleEntity, String> {
}
