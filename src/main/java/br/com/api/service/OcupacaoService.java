package br.com.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.domain.Hospital;
import br.com.api.domain.MediaOcupacao;
import br.com.api.domain.Ocupacao;
import br.com.api.domain.dto.OcupacaoDTO;
import br.com.api.repository.HospitalRepository;
import br.com.api.repository.OcupacaoRepository;

@Service
public class OcupacaoService {

	@Autowired
	private OcupacaoRepository ocupacaoRepository;

	@Autowired
	private HospitalRepository hospitalRepository;

	/**
	 * converte um objeto domain para um DTO
	 */
	public OcupacaoDTO fromModel(Ocupacao ocupacao) {
		return new OcupacaoDTO(ocupacao);
	}

	/**
	 * calcula a média de hospital que possuem ocupacao superior a 90%
	 */
	public MediaOcupacao hospitaisComOcupacaoSuperiorANoventa() {
		List<Hospital> hospitais = this.hospitalRepository.findAll();

		List<Hospital> hospitaisComOcupacaoMaiorQueNoventa = new ArrayList<Hospital>();

		if (hospitais.size() > 0) {
			/*
			 * busca os hospitais que tem ocupacao superior a 90%
			 */
			for (Hospital h : hospitais) {
				for (Ocupacao oc : h.getOcupacao()) {
					if (oc.getOcupacao() > 90) {
						hospitaisComOcupacaoMaiorQueNoventa.add(h);
						break;
					}
				}
			}
			double media = hospitaisComOcupacaoMaiorQueNoventa.size() / this.buscaHospitaisQuePossuemOcupacao();

			MediaOcupacao mediaOcupada = new MediaOcupacao(hospitaisComOcupacaoMaiorQueNoventa, media);

			return mediaOcupada;
		} else {
			throw new RuntimeException("Não existe hospitais cadastrados");
		}
	}

	/**
	 * calcula a média de hospital que possuem ocupacao inferior a 90%
	 */
	public MediaOcupacao hospitaisComOcupacaoInferiorANoventa() {
		List<Hospital> hospitais = this.hospitalRepository.findAll();

		List<Hospital> hospitaisComOcupacaoMenorQueNoventa = new ArrayList<Hospital>();

		if (hospitais.size() > 0) {
			/*
			 * busca os hospitais que tem ocupacao inferior a 90%
			 */
			for (Hospital h : hospitais) {
				for (Ocupacao oc : h.getOcupacao()) {
					if (oc.getOcupacao() < 90) {
						hospitaisComOcupacaoMenorQueNoventa.add(h);
						break;
					}
				}
			}
			
			double media = hospitaisComOcupacaoMenorQueNoventa.size() / this.buscaHospitaisQuePossuemOcupacao();

			
			MediaOcupacao mediaOcupada = new MediaOcupacao(hospitaisComOcupacaoMenorQueNoventa, media);

			return mediaOcupada;
		} else {
			throw new RuntimeException("Não existe hospitais cadastrados");
		}
	}
	
	public int buscaHospitaisQuePossuemOcupacao(){
		List<Hospital> hospitais = this.hospitalRepository.findAll();
		int total = 0;
		/*
		 * verifica quais hospitais possuem alguma ocupacao
		 */
		for(Hospital h : hospitais) {
			if(h.getOcupacao().size() > 0) {
				total ++;
			}
		}
		return total;
	}
}
