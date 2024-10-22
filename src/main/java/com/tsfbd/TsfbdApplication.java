package com.tsfbd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@CrossOrigin
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class TsfbdApplication {

	public static void main(String[] args) {
		SpringApplication.run(TsfbdApplication.class, args);
	}

}
