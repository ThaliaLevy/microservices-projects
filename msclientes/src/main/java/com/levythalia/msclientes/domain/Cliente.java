package com.levythalia.msclientes.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Cliente {

	public Cliente(String cpf, String nome, String idade) {
		this.cpf = cpf;
		this.nome = nome;
		this.idade = idade;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String cpf;
	private String nome;
	private String idade;
}
