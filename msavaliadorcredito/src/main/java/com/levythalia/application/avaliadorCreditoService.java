package com.levythalia.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.levythalia.application.exception.DadosClienteNotFoundException;
import com.levythalia.application.exception.ErroComunicacaoMicroservicesException;
import com.levythalia.domain.model.Cartao;
import com.levythalia.domain.model.CartaoAprovado;
import com.levythalia.domain.model.CartaoCliente;
import com.levythalia.domain.model.DadosCliente;
import com.levythalia.domain.model.RetornoAvaliacaoCliente;
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
	
	public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
		try {
			ResponseEntity<DadosCliente> clienteResponse = clientesClient.dadosCliente(cpf);
			ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.getCartoesRendaAteh(renda);
			
			List<Cartao> cartoes = cartoesResponse.getBody();
			var listaCartoesAprovados = cartoes.stream().map(cartao -> {
				
				DadosCliente dadosCliente = clienteResponse.getBody();
				
				BigDecimal limiteBasico = cartao.getLimiteBasico();
				BigDecimal rendaBD = BigDecimal.valueOf(renda);
				BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
				var fator = idadeBD.divide(BigDecimal.valueOf(10));
				BigDecimal limiteAprovado = fator.multiply(limiteBasico);
						
				CartaoAprovado aprovado = new CartaoAprovado();
				aprovado.setCartao(cartao.getNome());
				aprovado.setBandeira(cartao.getBandeira());
				aprovado.setLimiteAprovado(limiteAprovado);
				
				return aprovado;
			}).collect(Collectors.toList());
			
			return new RetornoAvaliacaoCliente(listaCartoesAprovados);
		} catch (FeignException.FeignClientException e) {
			int status = e.status();
			
			if(HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			
			throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
		}
	}
}
