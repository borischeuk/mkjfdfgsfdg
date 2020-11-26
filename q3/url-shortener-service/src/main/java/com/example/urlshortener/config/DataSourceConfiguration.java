package com.example.urlshortener.config;

import com.example.urlshortener.config.property.ShortenUrlDataSourceProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableConfigurationProperties(ShortenUrlDataSourceProperties.class)
public class DataSourceConfiguration {

    @Bean
    DataSource dataSource(HikariConfig hikariDataSourceConfig) {
        log.debug("Creating datasource");

        return new HikariDataSource(hikariDataSourceConfig);
    }

}
