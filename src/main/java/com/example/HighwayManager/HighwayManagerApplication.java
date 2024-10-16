package com.example.HighwayManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// (exclude = { SecurityAutoConfiguration.class }) to make the api accessible
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class HighwayManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HighwayManagerApplication.class, args);
	}

}
