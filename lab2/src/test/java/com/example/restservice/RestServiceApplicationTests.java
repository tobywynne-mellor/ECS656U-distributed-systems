package com.example.restservice;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestServiceApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void addItem() {
		Stock inventory = this.restTemplate.getForObject("http://localhost:" + port + "/", Stock.class);
	}

	@Test
	public void setStock() {

	}

	@Test
	public void addStock() {

	}

	@Test
	public void removeStock() {

	}

	@Test
	public void listStock() {

	}
}
