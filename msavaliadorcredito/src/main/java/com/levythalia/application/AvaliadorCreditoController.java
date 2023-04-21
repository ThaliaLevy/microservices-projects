package com.levythalia.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.levythalia.application.exception.DadosClienteNotFoundException;
import com.levythalia.application.exception.ErroComunicacaoMicroservicesException;
import com.levythalia.domain.model.DadosAvaliacao;
import com.levythalia.domain.model.RetornoAvaliacaoCliente;
import com.levythalia.domain.model.SituacaoCliente;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

	private final avaliadorCreditoService avaliadorCreditoService;

	@GetMapping
	public String status() {
		return "teste";
	}

	@GetMapping(value = "situacao-cliente", params = "cpf")
	public ResponseEntity<?> consultaSituacaoCliente(@RequestParam(value = "cpf", required = false) String cpf) {
		SituacaoCliente situacaoCliente;
		try {
			situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
			return ResponseEntity.ok(situacaoCliente);
		} catch (DadosClienteNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (ErroComunicacaoMicroservicesException e) {
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<?> realizarAvaliacao(@RequestBody DadosAvaliacao dados) {
		try {
			RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService.realizarAvaliacao(dados.getCpf(), dados.getRenda());
			return ResponseEntity.ok(retornoAvaliacaoCliente);
		} catch (DadosClienteNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (ErroComunicacaoMicroservicesException e) {
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}
	}
}
