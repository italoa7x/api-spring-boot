package br.com.api.domain.dto;

import br.com.api.domain.Ocupacao;

public class OcupacaoDTO {

	private double ocupacao;

	public OcupacaoDTO(Ocupacao ocupacao) {
		this.ocupacao = ocupacao.getOcupacao();
	}

	public double getOcupacao() {
		return ocupacao;
	}

	public void setOcupacao(double ocupacao) {
		this.ocupacao = ocupacao;
	}

}
