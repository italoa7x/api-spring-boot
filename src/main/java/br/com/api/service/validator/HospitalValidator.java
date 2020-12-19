package br.com.api.service.validator;

import org.springframework.stereotype.Service;

import br.com.api.domain.Hospital;

@Service
public class HospitalValidator {

	/*
	 * Valida os dados do hospital
	 */
	public boolean validarHospital(Hospital hospital) {
		if(hospital.getCnpj() == null || hospital.getEndereco() == null || hospital.getNome() == null ) {
			return false;
		}else {
			return true;
		}
	}
}
