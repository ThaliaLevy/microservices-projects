package com.levythalia;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableEurekaClient
@EnableRabbit
@Slf4j
public class MscartoesApplication {

	public static void main(String[] args) {
		//tipos mais utilizados
		log.info("informação {}", "teste info");
		log.error("error {}", "teste error");
		log.warn("warn {}", "teste warn");
		SpringApplication.run(MscartoesApplication.class, args);
	}

}
