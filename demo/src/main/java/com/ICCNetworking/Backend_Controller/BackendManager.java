package com.ICCNetworking.Backend_Controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class BackendManager {

	public static void main(String[] args) {
		SpringApplication.run(BackendManager.class, args);
	}

	
}
