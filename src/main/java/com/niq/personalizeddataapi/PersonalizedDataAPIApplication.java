package com.niq.personalizeddataapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class PersonalizedDataAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalizedDataAPIApplication.class, args);
	}

}
