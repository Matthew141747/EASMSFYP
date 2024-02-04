package com.fypp.Ethics_Management_System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EthicsManagementSystemApplication {

	public static void main(String[] args) {
		System.setProperty("org.springframework.security.debug", "true");
		SpringApplication.run(EthicsManagementSystemApplication.class, args);
	}

}
