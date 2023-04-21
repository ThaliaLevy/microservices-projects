package com.levythalia.application;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.levythalia.application.exception.DadosClienteNotFoundException;
import com.levythalia.application.exception.ErroComunicacaoMicroservicesException;
import com.levythalia.domain.model.CartaoCliente;
import com.levythalia.domain.model.DadosCliente;
import com.levythalia.domain.model.SituacaoCliente;
import com.levythalia.infra.clients.CartoesResourceClient;
import com.levythalia.infra.clients.ClienteResourceClient;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class avaliadorCreditoService {
	
	private final ClienteResourceClient clientesClient;
	private final CartoesResourceClient cartoesClient;

	public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
		try {
			ResponseEntity<DadosCliente> clientesResponse = clientesClient.dadosCliente(cpf);
			ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);
			
			return SituacaoCliente
					.builder()
					.cliente(clientesResponse.getBody())
					.cartoes(cartoesResponse.getBody())
					.build();
		} catch (FeignException.FeignClientException e) {
			int status = e.status();
			
			if(HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			
			throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
		}
	}
}
