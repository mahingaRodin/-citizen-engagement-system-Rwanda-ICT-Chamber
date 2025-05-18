package com.citizen.engagement_system_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class EngagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EngagementSystemApplication.class, args);
	}

}
