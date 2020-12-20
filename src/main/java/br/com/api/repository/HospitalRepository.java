package br.com.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.domain.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer>{

	@Transactional(readOnly = true)
	Hospital findByCnpj(String cnpj);
	
}
