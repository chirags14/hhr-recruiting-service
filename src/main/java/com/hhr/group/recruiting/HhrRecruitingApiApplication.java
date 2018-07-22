package com.hhr.group.recruiting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * /**
 * @author Chirag Suthar
 * 
 * Main class for HR Haven Recruiting service (HHR)
 *
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableAutoConfiguration
@EnableJpaAuditing
@ComponentScan(basePackages= {"com.hhr.group.recruiting"})
public class HhrRecruitingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HhrRecruitingApiApplication.class, args);
	}
}
