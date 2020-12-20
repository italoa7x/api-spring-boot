package br.com.api.domain;

import java.util.List;

public class MediaOcupacao {

	private List<Hospital> hospitais;
	private double media;

	public MediaOcupacao(List<Hospital> hospitais, double media) {
		super();
		this.hospitais = hospitais;
		this.media = media;
	}

	public MediaOcupacao() {
		super();
	}

	public List<Hospital> getHospitais() {
		return hospitais;
	}

	public void setHospitais(List<Hospital> hospitais) {
		this.hospitais = hospitais;
	}

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}

}
