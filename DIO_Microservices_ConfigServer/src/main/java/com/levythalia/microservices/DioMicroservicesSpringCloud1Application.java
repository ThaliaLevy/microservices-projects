package com.levythalia.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class DioMicroservicesSpringCloud1Application {

	public static void main(String[] args) {
		SpringApplication.run(DioMicroservicesSpringCloud1Application.class, args);
	}

}
