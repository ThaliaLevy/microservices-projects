package com.levythalia.infra.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levythalia.domain.Cartao;
import com.levythalia.domain.ClienteCartao;
import com.levythalia.domain.DadosSolicitacaoEmissaoCartao;
import com.levythalia.infra.repository.CartaoRepository;
import com.levythalia.infra.repository.ClienteCartaoRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EmissaoCartaoSubscriber {
	
	private final CartaoRepository cartaoRepository;
	private final ClienteCartaoRepository clienteCartaoRepository;

	//anotação usada para indicar que essa queue ficará escutando essa fila
	@RabbitListener(queues = "${mq.queues.emissao-cartoes}")
	public void receberSolicitacaoEmissao(@Payload String payload) {
		try {
			var mapper = new ObjectMapper();
			DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
			
			Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();
			ClienteCartao clienteCartao = new ClienteCartao();
			
			clienteCartao.setCartao(cartao);
			clienteCartao.setCpf(dados.getCpf());
			clienteCartao.setLimite(dados.getLimiteLiberado());			
			
			clienteCartaoRepository.save(clienteCartao);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
