package com.capgemini.navikaran;

import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = "com.capgemini.*")
@EnableAutoConfiguration
@SpringBootApplication
public class NavikaranApplication implements WebMvcConfigurer  {
	
    private static Logger logger = LoggerFactory.getLogger(NavikaranApplication.class.getName());

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Register resource handler for images
        registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/")
                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
    }

	public static void main(String[] args) {
		
		logger.info("Application is starting ..");
		SpringApplication.run(NavikaranApplication.class, args);
	}
}
