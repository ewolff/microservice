package com.ewolff.microservice.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CatalogTestApp {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(CatalogTestApp.class);
		app.setAdditionalProfiles("test");
		app.run(args);
	}

}
