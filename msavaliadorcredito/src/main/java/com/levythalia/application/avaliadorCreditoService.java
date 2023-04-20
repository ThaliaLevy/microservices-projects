package com.levythalia.application;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.levythalia.domain.model.DadosCliente;
import com.levythalia.domain.model.SituacaoCliente;
import com.levythalia.infra.clients.ClienteResourceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class avaliadorCreditoService {
	
	private final ClienteResourceClient clientesClient;

	public SituacaoCliente obterSituacaoCliente(String cpf) {
		ResponseEntity<DadosCliente> response = clientesClient.dadosCliente(cpf);
		
		return SituacaoCliente
				.builder()
				.cliente(response.getBody())
				.build();
	}
}
