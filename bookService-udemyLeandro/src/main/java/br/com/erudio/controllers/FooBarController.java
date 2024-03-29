package br.com.erudio.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name= "Foo bar")
@RestController
@RequestMapping("book-service")
public class FooBarController {
	
	private Logger logger = LoggerFactory.getLogger(FooBarController.class);	

	@GetMapping("/foo-bar")
	@Operation(summary= "Foo-bar")
	//@Retry(name="default")	// caso dê erro, ele tenta mais vezes. como default tenta 3x
	//@Retry(name="foo-bar")	// caso dê erro, pega as configurações do resilience4j no .yml
	//@Retry(name="foo-bar", fallbackMethod="fallbackMethod")	//chama a função fallback abaixo
	@CircuitBreaker(name="default", fallbackMethod="fallbackMethod")	
	public String fooBar() {
		logger.info("testandooooooooidfo");
		var response = new RestTemplate().getForEntity("http://localhost:8080/foo-bar", String.class);
		return response.getBody();
	}
	
	public String fallbackMethod(Exception ex) {
		return "fallbackMethod foo-bar";
	}
}
