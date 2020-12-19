package br.com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.domain.Recurso;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Integer>{

}
