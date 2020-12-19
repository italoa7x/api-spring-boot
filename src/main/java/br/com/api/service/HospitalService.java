package br.com.api.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.domain.Hospital;
import br.com.api.domain.Ocupacao;
import br.com.api.domain.dto.OcupacaoDTO;
import br.com.api.repository.HospitalRepository;
import br.com.api.repository.exception.CnpjDuplicadoException;
import br.com.api.service.validator.HospitalValidator;

@Service
public class HospitalService {

	@Autowired
	private HospitalRepository hospitalRepository;

	@Autowired
	private HospitalValidator validador;
	
	@Autowired
	private OcupacaoService ocupacaoService;

	/*
	 * Salva um novo hospital
	 */
	public Hospital salvarHospital(Hospital hospital) {
		try {
			if (this.validador.validarHospital(hospital)) {

				Hospital existente = this.hospitalRepository.findByCnpj(hospital.getCnpj());

				if (existente != null) {
					throw new CnpjDuplicadoException("CNPJ já cadastrado");
				}
				return this.hospitalRepository.save(hospital);
			} else {
				return null;
			}
		} catch (CnpjDuplicadoException e) {
			throw new RuntimeException("Erro ao cadastrar hospital.");
		}
	}

	public List<Hospital> listarHospitais() {
		return this.hospitalRepository.findAll();
	}

	/**
	 * Busca um hospital pelo ID
	 */
	public Hospital buscarPorID(Integer id) {
		Optional<Hospital> hospitalBuscado = this.hospitalRepository.findById(id);
		return hospitalBuscado.isPresent() ? hospitalBuscado.get() : null;
	}

	/*
	 * Exclui um hospital
	 */
	public void excluirHospital(Integer idHospital) {
		this.hospitalRepository.deleteById(idHospital);
	}

	public List<OcupacaoDTO> atualizarOcupacao(Integer idHospital, Ocupacao novaOcupacao) {
		Optional<Hospital> hospitalBuscado = this.hospitalRepository.findById(idHospital);

		if(novaOcupacao == null) {
			return null;
		}
		if (hospitalBuscado.isEmpty()) {
			return null;
		} else {
			/*
			 * pega o hospital buscado
			 */
			Hospital hospital = hospitalBuscado.get();
			/*
			 * adiciona a nova ocupacao
			 */
			hospital.getOcupacao().addAll(Arrays.asList(novaOcupacao));

			/*
			 * atualiza com a nova ocupacao
			 */
			this.hospitalRepository.save(hospital);
			
			List<OcupacaoDTO> ocupacoesSalvas = hospital.getOcupacao().stream().map(oc -> this.ocupacaoService.fromModel(oc))
					.collect(Collectors.toList());
			
			return ocupacoesSalvas;
		}
	}

	/*
	 * retorna o ultimo percentual de ocupacao inserido
	 */
	public OcupacaoDTO buscarOcupacao(Integer idHospital) {
		try {
			Optional<Hospital> hospitalBuscado = this.hospitalRepository.findById(idHospital);

			if (hospitalBuscado.isEmpty()) {
				return null;
			}else {
				/**
				 * verifica se existe ocupacoes
				 */
				Hospital hospital = hospitalBuscado.get();
				if(hospital.getOcupacao() == null || hospital.getOcupacao().size() == 0 ) {
					return null;
				}
				/*
				 * pega a ultima ocupacao inserida na lista
				 */
				Ocupacao ultimaOcupacao = hospital.getOcupacao().get(hospital.getOcupacao().size() - 1);
				return ocupacaoService.fromModel(ultimaOcupacao);
			}
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro");
		}
	}

}
