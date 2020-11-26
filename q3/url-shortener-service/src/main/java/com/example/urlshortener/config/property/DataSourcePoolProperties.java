package com.example.urlshortener.config.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "db-pool")
@SuppressWarnings("checkstyle:MagicNumber")
public class DataSourcePoolProperties {

    private int connectionTimeout = 5000;

    private int maximumPoolSize = Runtime.getRuntime().availableProcessors() * 2 + 2;

    private int minimumIdle = 2;

    private int idleTimeout = 45000;

}