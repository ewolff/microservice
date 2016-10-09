package com.ewolff.microservice.eurekaserver;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EurekaApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@Value("${local.server.port}")
	private int port = 0;

	@Test
	public void catalogLoads() {
		ResponseEntity<Map> entity = new TestRestTemplate().getForEntity("http://localhost:" + port + "/eureka/apps",
				Map.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}

	@Test
	public void adminLoads() {
		ResponseEntity<Map> entity = new TestRestTemplate().getForEntity("http://localhost:" + port + "/env",
				Map.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}

}
