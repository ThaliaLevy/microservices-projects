package com.levythalia.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.levythalia.application.representation.CartaoSaveRequest;
import com.levythalia.application.representation.CartoesPorClienteResponse;
import com.levythalia.domain.Cartao;
import com.levythalia.domain.ClienteCartao;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesResource {
	
	private final CartaoService cartaoService;
	private final ClienteCartaoService clienteCartaoService;

	@GetMapping
	public String status() {
		return "ok";
	}
	
	@PostMapping
	public ResponseEntity<?> cadastra(@RequestBody CartaoSaveRequest request) {
		Cartao cartao = request.toModel();
		cartaoService.save(cartao);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(params = "renda")
	public ResponseEntity<List<Cartao>> getCartoesRendaAteh(@RequestParam("renda") Long renda) {
		List<Cartao> list = cartaoService.getCartoesRendaMenorIgual(renda);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(@RequestParam("cpf") String cpf) {
		List<ClienteCartao> list = clienteCartaoService.listCartaoesByCpf(cpf);
		List<CartoesPorClienteResponse> resultList = list.stream()
														 .map(CartoesPorClienteResponse::fromModel)
														 .collect(Collectors.toList());
		return ResponseEntity.ok(resultList);
	}
}
