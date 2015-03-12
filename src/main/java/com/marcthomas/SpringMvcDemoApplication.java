package com.marcthomas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class is responsible for launching the Spring Boot application as a
 * standalone java app with an embedded tomcat.
 * 
 * @author marcth
 */
@SpringBootApplication
public class SpringMvcDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcDemoApplication.class, args);
	}
}
