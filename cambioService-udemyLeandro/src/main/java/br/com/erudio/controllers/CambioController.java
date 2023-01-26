package br.com.erudio.controllers;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.models.Cambio;

@RestController
@RequestMapping("cambio-service")
public class CambioController {

	// localhost:8000/cambio-service/5/USD/BRL
	@GetMapping(value = "/{amount}/{from}/{to}")
	public Cambio getCambio(@PathVariable(name= "amount") BigDecimal amount,
							@PathVariable(name= "from") String from,
							@PathVariable(name= "to") String to) {
		
		return new Cambio(1L, from, to, BigDecimal.ONE, BigDecimal.ONE, "PORT 8000");
	}
}
