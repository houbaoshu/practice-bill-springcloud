package com.example.personalAccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PersonalAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalAccountApplication.class, args);
	}

}
