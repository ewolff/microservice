package com.ewolff.microservice.eurekaserver;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EurekaApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@LocalServerPort
	private int port = 0;
	private RestTemplate restTemplate = new RestTemplate();

	@Test
	public void lastnLoads() {
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:" + port + "/lastn/",
				String.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}

	@Test
	public void eurekaLoads() {
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:" + port,
				String.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}

}
