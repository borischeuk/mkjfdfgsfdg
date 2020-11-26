package com.example.urlshortener.config;

import com.example.urlshortener.config.property.DataSourcePoolProperties;
import com.example.urlshortener.config.property.ShortenUrlDataSourceProperties;
import com.zaxxer.hikari.HikariConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.urlshortener.config.HikariConfigBuilder.aNewHikariConfig;

@Configuration
@EnableConfigurationProperties(DataSourcePoolProperties.class)
public class DefaultHikariDataSourceConfiguration {

    @Bean
    HikariConfig hikariDataSourceConfig(ShortenUrlDataSourceProperties dataSourceProperties,
                                        DataSourcePoolProperties poolConfig) {
        return aNewHikariConfig(dataSourceProperties)
                .withDriverClassName("com.mysql.cj.jdbc.Driver")
                .withConnectionTimeout(poolConfig.getConnectionTimeout())
                .withIdleTimeout(poolConfig.getIdleTimeout())
                .withMaximumPoolSize(poolConfig.getMaximumPoolSize())
                .withMinimumIdle(poolConfig.getMinimumIdle())
                .build();
    }

}
