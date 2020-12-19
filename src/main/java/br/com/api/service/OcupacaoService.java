package br.com.api.service;

import org.springframework.stereotype.Service;

import br.com.api.domain.Ocupacao;
import br.com.api.domain.dto.OcupacaoDTO;

@Service
public class OcupacaoService {

	/**
	 * converte um objeto domain para um DTO
	 */
	public OcupacaoDTO fromModel(Ocupacao ocupacao) {
		return new OcupacaoDTO(ocupacao);
	}
}
