package com.levythalia.application.representation;

import java.math.BigDecimal;

import com.levythalia.domain.ClienteCartao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartoesPorClienteResponse {
	
	public static CartoesPorClienteResponse fromModel(ClienteCartao model) {
		return new CartoesPorClienteResponse(
				model.getCartao().getNome(),
				model.getCartao().getBandeira().toString(),
				model.getLimite()
		);
	}
	
	private String nome;
	private String bandeira;
	private BigDecimal limiteLiberado;

}
