package com.gcit.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.gcit.lms")  //optional if you forgot to add the base package
public class LmsSpringBootLibrarianApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsSpringBootLibrarianApplication.class, args);
	}
}
