package com.example.HighwayManager;

import com.example.HighwayManager.config.DatabaseConfig;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

// (exclude = { SecurityAutoConfiguration.class }) to make the api accessible
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableConfigurationProperties(DatabaseConfig.class)
public class HighwayManagerApplication {

	public static void main(String[] args) {
		if (System.getenv("ENVIRONMENT") == null || System.getenv("ENVIRONMENT").equals("development")) {
			try {
				Dotenv dotenv = Dotenv.load();
				System.setProperty("spring.datasource.url", dotenv.get("DB_URL"));
				System.setProperty("spring.datasource.username", dotenv.get("DB_USERNAME"));
				System.setProperty("spring.datasource.password", dotenv.get("DB_PASSWORD"));
				System.setProperty("jwt.secret", dotenv.get("JWT_SECRET"));
				System.setProperty("spring.profiles.active", dotenv.get("ENVIRONMENT"));
			} catch (Exception e) {
				System.out.println(".env file not found, using system environment variables");
			}
		}

		SpringApplication.run(HighwayManagerApplication.class, args);
	}
}

