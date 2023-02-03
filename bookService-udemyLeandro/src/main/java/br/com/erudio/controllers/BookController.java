package br.com.erudio.controllers;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.models.Book;
import br.com.erudio.proxy.CambioProxy;
import br.com.erudio.repositories.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Book endpoint")
@RestController
@RequestMapping("book-service")
public class BookController {
	
	private final Environment environment;
	private final BookRepository bookRepository;
	private final CambioProxy cambioProxy;
	
	public BookController(Environment environment, BookRepository bookRepository, CambioProxy cambioProxy) {
		this.environment = environment;
		this.bookRepository = bookRepository;
		this.cambioProxy = cambioProxy;
	}

	//localhost:8000/book-service/1/BRL
	@Operation(summary= "Find a specific book by your ID")
	@GetMapping(value= "{id}/{currency}")
	public Book findBook(@PathVariable(name="id") Long id,
						 @PathVariable(name="currency") String currency) {
		
		var book = bookRepository.getById(id);
		
		if(book == null) 
			throw new RuntimeException("Book not found!");
		
		var cambio = cambioProxy.getCambio(book.getPrice(), "USD", currency);
		
		var port = environment.getProperty("local.server.port");
		book.setEnvironment(port);
		book.setPrice(cambio.getConvertedValue());
		
		return book;
	}
}
