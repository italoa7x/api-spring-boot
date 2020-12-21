package br.com.api.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

@Entity
public class Hospital implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String nome;
	/**
	 * O cnpj é unico por empresa Size: O campo de CNPJ podera ser com ou sem pont e
	 * barra
	 */
	@Column(unique = true)
	@Size(min = 14, max = 18)
	@NotNull
	private String cnpj;

	/**
	 * Endereço é uma classe enbutida, ela não precisa ser mapeada
	 */
	@Embedded
	@NotNull
	private Endereco endereco;

	/*
	 * FetchType.EAGER: traz todo o historico de ocupacoes do hospital
	 */
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private List<Ocupacao> ocupacao = new ArrayList<Ocupacao>();

	@OneToOne(cascade = CascadeType.PERSIST)
	@NotNull()
	private Localizacao localizacao;

	/*
	 * Um hospital tem uma lista de recursso
	 * 
	 * FechType.LAZY: irá trazer todos os recursos não de uma vez, mas aos poucos
	 * (sempre que for solicitado)
	 */
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@NotNull
	private List<Recurso> recursos = new ArrayList<Recurso>();

	
	public Hospital() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Ocupacao> getOcupacao() {
		return ocupacao;
	}

	public void setOcupacao(List<Ocupacao> ocupacao) {
		this.ocupacao = ocupacao;
	}

	public Localizacao getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(Localizacao localizacao) {
		this.localizacao = localizacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hospital other = (Hospital) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
