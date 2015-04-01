package com.ewolff.microservice.customer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.ewolff.microservice.customer.domain.Customer;
import com.ewolff.microservice.customer.repository.CustomerRepository;

@ComponentScan
@EnableAutoConfiguration
@EnableEurekaClient
@Component
public class CustomerApp {

	private CustomerRepository customerRepository;

	@Autowired
	public CustomerApp(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@PostConstruct
	public void generateTestData() {
		customerRepository.save(new Customer("Eberhard", "Wolff",
				"eberhard.wolff@gmail.com", "Unter den Linden", "Berlin"));
		customerRepository.save(new Customer("Rod", "Johnson",
				"rod@somewhere.com", "Market Street", "San Francisco"));
	}

	public static void main(String[] args) {
		SpringApplication.run(CustomerApp.class, args);
	}

}
