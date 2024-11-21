package com.example.HighwayManager.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class CookieConfig {
    private final boolean isProduction;
    private final String cookieName = "jwt";
    private final int maxAge = 5 * 60 * 1000;

    public CookieConfig() {
        String env = System.getenv("ENVIRONMENT");
        this.isProduction = env != null && !env.equals("development");
    }

    public boolean isSecureAndHttpOnly() {
        return isProduction;
    }
}
