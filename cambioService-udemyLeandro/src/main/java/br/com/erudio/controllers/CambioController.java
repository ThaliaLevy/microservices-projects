package br.com.erudio.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.models.Cambio;
import br.com.erudio.repositories.CambioRepository;

@RestController
@RequestMapping("cambio-service")
public class CambioController {

	private final Environment environment;
	private final CambioRepository cambioRepository;

	public CambioController(Environment environment, CambioRepository cambioRepository) {
		this.environment = environment;
		this.cambioRepository = cambioRepository;
	}

	// localhost:8000/cambio-service/5/USD/BRL
	@GetMapping(value = "/{amount}/{from}/{to}")
	public Cambio getCambio(@PathVariable(name = "amount") BigDecimal amount, 
							@PathVariable(name = "from") String from,
							@PathVariable(name = "to") String to) {

		var cambio = cambioRepository.findByFromAndTo(from, to);
		
		if(cambio == null) 
			throw new RuntimeException("Currency unsupported");
		
		var port = environment.getProperty("local.server.port"); // pega a porta da aplicação
		BigDecimal conversionFactor = cambio.getConversionFactor();
		BigDecimal convertedValue = conversionFactor.multiply(amount);
		cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));	//setScale > arredonda para duas casas decimais
		cambio.setEnvironment(port);

		return cambio;
	}
}
