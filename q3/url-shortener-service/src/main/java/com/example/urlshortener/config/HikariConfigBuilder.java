package com.example.urlshortener.config;

import com.example.urlshortener.config.property.ShortenUrlDataSourceProperties;
import com.zaxxer.hikari.HikariConfig;
import lombok.NoArgsConstructor;

import static com.google.common.base.Preconditions.checkNotNull;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class HikariConfigBuilder {

    private String jdbcUrl;
    private String userName;
    private String password;
    private String driverClassName;
    private int connectionTimeout;
    private int maximumPoolSize;
    private int minimumIdle;
    private int idleTimeout;

    private HikariConfigBuilder(ShortenUrlDataSourceProperties properties) {
        this.jdbcUrl = formatUrl(properties);
        this.userName = properties.getUsername();
        this.password = properties.getPassword();
    }

    private String formatUrl(ShortenUrlDataSourceProperties properties) {
        return String.format("jdbc:mysql://%s:%s/%s?useJDBCCompliantTimezoneShift=true&serverTimezone=%s&useUnicode=true&characterEncoding=utf8",
                properties.getHost(),
                properties.getPort(),
                properties.getDbName(),
                properties.getAppTimeZone());
    }

    public static HikariConfigBuilder aNewHikariConfig() {
        return new HikariConfigBuilder();
    }

    public static HikariConfigBuilder aNewHikariConfig(ShortenUrlDataSourceProperties dataSourceProperties) {
        return new HikariConfigBuilder(dataSourceProperties);
    }

    public HikariConfigBuilder withJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
        return this;
    }

    public HikariConfigBuilder withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public HikariConfigBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public HikariConfigBuilder withConnectionTimeout(int timeoutMs) {
        this.connectionTimeout = timeoutMs;
        return this;
    }

    public HikariConfigBuilder withMaximumPoolSize(int maxSize) {
        this.maximumPoolSize = maxSize;
        return this;
    }

    public HikariConfigBuilder withMinimumIdle(int minIdle) {
        this.minimumIdle = minIdle;
        return this;
    }

    public HikariConfigBuilder withIdleTimeout(int idleTimeoutMs) {
        this.idleTimeout = idleTimeoutMs;
        return this;
    }

    public HikariConfigBuilder withDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        return this;
    }

    public HikariConfig build() {
        checkNotNull(jdbcUrl);
        checkNotNull(userName);
        checkNotNull(password);

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(userName);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setMinimumIdle(minimumIdle);
        hikariConfig.setIdleTimeout(idleTimeout);
        hikariConfig.setMaximumPoolSize(maximumPoolSize);
        hikariConfig.setConnectionTimeout(connectionTimeout);

        return hikariConfig;
    }
}
