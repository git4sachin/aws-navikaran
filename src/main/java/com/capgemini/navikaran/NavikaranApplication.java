package com.capgemini.navikaran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.capgemini.*")
@EnableAutoConfiguration
@SpringBootApplication
public class NavikaranApplication {

	public static void main(String[] args) {
		SpringApplication.run(NavikaranApplication.class, args);
	}
}
