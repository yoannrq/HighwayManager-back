package com.example.HighwayManager.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DatabaseConfig {
    // Getters et setters
    private String url;
    private String username;
    private String password;
}
