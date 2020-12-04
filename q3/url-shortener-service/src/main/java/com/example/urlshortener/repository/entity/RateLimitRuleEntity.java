package com.example.urlshortener.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@With
@Table(name = "rate_limit_rule")
public class RateLimitRuleEntity {

    @Id
    private String ip;

    private Integer rateLimit;

    private Integer timeInterval;

}
