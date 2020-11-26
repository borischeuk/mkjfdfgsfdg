package com.example.urlshortener.config.property;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "shortenurl-datasource")
public class ShortenUrlDataSourceProperties {

    private String host;

    private int port;

    @JsonProperty("dbname")
    private String dbName;

    private String username;

    private String password;

    public String getAppTimeZone() {
        ZoneId zone = ZonedDateTime.now().getZone();
        return zone.toString();
    }

}
