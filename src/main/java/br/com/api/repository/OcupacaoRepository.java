package br.com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.domain.Ocupacao;

public interface OcupacaoRepository extends JpaRepository<Ocupacao, Integer> {

	
}
