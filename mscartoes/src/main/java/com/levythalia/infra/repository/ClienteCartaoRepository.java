package com.levythalia.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.levythalia.domain.ClienteCartao;

public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Long> {

	List<ClienteCartao> findByCpf(String Cpf);
}
