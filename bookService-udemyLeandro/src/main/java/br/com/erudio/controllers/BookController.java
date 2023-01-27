package br.com.erudio.controllers;

import java.util.Date;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.models.Book;

@RestController
@RequestMapping("book-service")
public class BookController {
	
	private final Environment environment;
	
	public BookController(Environment environment) {
		this.environment = environment;
	}

	//localhost:8000/book-service/1/BRL
	@GetMapping(value= "{id}/{currency}")
	public Book findBook(@PathVariable(name="id") Long id,
						 @PathVariable(name="currency") String currency) {
		
		var port = environment.getProperty("local.server.port");
		
		return new Book(1L, "Uncle Bob", "Codigo Limpo", new Date(), Double.valueOf(13.7), currency, port);
	}
}
