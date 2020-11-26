package com.example.urlshortener.config;

import com.example.urlshortener.config.property.ServiceConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ServiceConfigProperties.class)
public class CommonConfig {
}
