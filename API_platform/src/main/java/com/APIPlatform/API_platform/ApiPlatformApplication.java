package com.APIPlatform.API_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class ApiPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiPlatformApplication.class, args);
	}

}
