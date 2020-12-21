package br.com.api;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.api.domain.Endereco;
import br.com.api.domain.Hospital;
import br.com.api.domain.Localizacao;
import br.com.api.repository.HospitalRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HospitalRepositoryTestes {

	@Autowired(required = true)
	private HospitalRepository repo;

	private Hospital hospital;

	private Endereco endereco;

	private Localizacao localizacao;

	@Before
	public void init() {
		hospital = new Hospital();
		hospital.setCnpj("70.969.097/0001-20");
		hospital.setNome("hospital 1");

		Endereco endereco = new Endereco();
		endereco.setCep("58500-000");
		endereco.setCidade("monteiro");
		endereco.setLogradouro("logradouro 1");
		endereco.setNumero(100);

		hospital.setEndereco(endereco);
	}

	@Test
	public void saverDeveCadastrarUmNovoHospitalComSucesso() {
		hospital = repo.save(hospital);
		assertTrue(hospital.getId() != null);
	}

	@Test
	public void saveDeveRetornarUmHospitalSemDadosAoSalvarUmObjetoHospitalSemPassarDados() {
		Hospital hospitalSemDados = new Hospital();

		hospitalSemDados = repo.save(hospitalSemDados);
		assertNull(hospitalSemDados.getNome());
		assertNull(hospitalSemDados.getCnpj());
	}

	@Test
	public void findAllDeveRetornarUmaListaContendoDoisHospitals() {
		Hospital hospital2 = new Hospital();
		hospital2.setCnpj("12.228.676/0001-16");

		Endereco endereco2 = new Endereco();
		endereco2.setCep("19238912831");
		endereco2.setCidade("cidade 2");
		endereco2.setLogradouro("logradouro teste");
		endereco2.setNumero(102);
		hospital2.setEndereco(endereco2);

		/*
		 * aproveitando o hospital criado no comeco dos testes
		 */
		repo.save(hospital);
		repo.save(hospital2);

		List<Hospital> hospitaisSalvos = repo.findAll();

		assertTrue(hospitaisSalvos.size() > 0);
	}

	
	@Test
	public void deleteByIdDeveExcluirUmHospital() {
		repo.save(hospital);
		System.out.println("salvou  " + hospital.getId());
		repo.deleteById(hospital.getId());
		assertTrue(repo.findByCnpj(hospital.getCnpj()) == null);
	}
}
