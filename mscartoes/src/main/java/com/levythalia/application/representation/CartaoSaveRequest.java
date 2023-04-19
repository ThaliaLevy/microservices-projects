package com.levythalia.application.representation;

import java.math.BigDecimal;

import com.levythalia.domain.BandeiraCartao;
import com.levythalia.domain.Cartao;

import lombok.Data;

@Data
public class CartaoSaveRequest {

	public Cartao toModel() {
		return new Cartao(nome, bandeira, renda, limite);
	}
	
	private String nome;
	private BandeiraCartao bandeira;
	private BigDecimal renda;
	private BigDecimal limite;
}
