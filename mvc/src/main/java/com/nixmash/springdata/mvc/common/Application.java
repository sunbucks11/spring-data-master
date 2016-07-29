package com.nixmash.springdata.mvc.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableConfigurationProperties
@ComponentScan(basePackages = "com.nixmash.springdata")
@EnableJpaRepositories(basePackages = "com.nixmash.springdata.jpa")
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) {
		// Source: http://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-devtools.html#using-boot-devtools-livereload
		System.setProperty("spring.devtools.restart.enabled", "false");
		//System.setProperty("spring.devtools.livereload.enabled", "false");
		SpringApplication.run(Application.class, args);
	}


}
