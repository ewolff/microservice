package com.ewolff.microservice.eurekaserver;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EurekaApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@Value("${local.server.port}")
	private int port = 0;
	private RestTemplate restTemplate = new RestTemplate();

	@Test
	public void catalogLoads() {
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:" + port + "/eureka/apps",
				String.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}

	@Test
	public void adminLoads() {
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:" + port + "/env",
				String.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}

}
