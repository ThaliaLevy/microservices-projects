package com.levythalia.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.levythalia.domain.ClienteCartao;
import com.levythalia.infra.repository.ClienteCartaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

	private final ClienteCartaoRepository repository;
	
	public List<ClienteCartao> listCartaoesByCpf(String cpf) {
		return repository.findByCpf(cpf);
	}
}
