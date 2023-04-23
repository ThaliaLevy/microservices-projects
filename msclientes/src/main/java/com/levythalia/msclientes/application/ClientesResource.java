package com.levythalia.msclientes.application;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.levythalia.msclientes.application.representation.ClienteSaveRequest;
import com.levythalia.msclientes.domain.Cliente;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClientesResource {
	
	private final ClienteService service;

	@GetMapping
	public String status() {
		return "ok";
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody ClienteSaveRequest request) {
		Cliente cliente = request.toModel();
		service.save(cliente);
		URI headerLocation = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.query("cpf={cpf}")
				.buildAndExpand(cliente.getCpf())
				.toUri();
		
		return ResponseEntity.created(headerLocation).build();
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity<?> dadosCliente(@RequestParam String cpf) {
		var cliente = service.getByCPF(cpf);
		if(cliente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(cliente);
	}
}
