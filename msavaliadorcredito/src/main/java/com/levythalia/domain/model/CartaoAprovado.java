package com.levythalia.domain.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartaoAprovado {

	private String cartao;
	private String bandeira;
	private BigDecimal limiteAprovado;
}
