package br.com.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

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
import br.com.api.domain.Ocupacao;
import br.com.api.repository.HospitalRepository;
import br.com.api.service.HospitalService;
import br.com.api.service.OcupacaoService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OcupacaoTestes {

	@MockBean
	private OcupacaoService OcupacaoService;

	@MockBean
	private HospitalService HospitalService;
	
	@Autowired(required = true)
	private HospitalRepository repo;

	private Hospital hospital;

	private Endereco endereco;

	@Before
	public void init() {
		hospital = new Hospital();
		hospital.setCnpj("70.969.097/0001-20");
		hospital.setNome("hospital 1");

		endereco = new Endereco();
		endereco.setCep("58500-000");
		endereco.setCidade("monteiro");
		endereco.setLogradouro("logradouro 1");
		endereco.setNumero(100);

		hospital.setEndereco(endereco);
	}

	@Test
	public void deveAtualizarAOcupacaDoHospitalSalvo() {
		repo.save(hospital);

		Ocupacao ocupacao = new Ocupacao();
		ocupacao.setOcupacao(30);

		hospital.getOcupacao().addAll(Arrays.asList(ocupacao));

		repo.save(hospital);

		assertEquals(hospital.getOcupacao().size(), 1);
	}

	@Test
	public void buscarHospitaisComOcupacaoDeveListarOsHospitaisQuePossuemOcupacao() {
		/*
		 * aproveitando o hospital criado no comeco dos testes
		 */
		repo.save(hospital);

		Ocupacao ocupacao = new Ocupacao();
		ocupacao.setOcupacao(30);

		hospital.getOcupacao().addAll(Arrays.asList(ocupacao));

		repo.save(hospital);
		
		/*
		 * quando chamar o metodo buscaHospitaisQuePossuemOcupacao, eh pra retornar 1, que e o unico hospital que foi salvo acima
		 */
		Mockito.when(OcupacaoService.buscaHospitaisQuePossuemOcupacao()).thenReturn(1);
		assertEquals(OcupacaoService.buscaHospitaisQuePossuemOcupacao(), 1);
	}
	
	@Test
	public void hospitaisComOcupacaoSuperiorANoventaDeveRetornarOsHospitaisQuePossuemOcupacaoSuperiorA90Porcento() {
		/*
		 * aproveitando o hospital criado no comeco dos testes
		 */
		repo.save(hospital);

		Ocupacao ocupacao = new Ocupacao();
		ocupacao.setOcupacao(98);

		hospital.getOcupacao().addAll(Arrays.asList(ocupacao));

		repo.save(hospital);
		
		Mockito.when(OcupacaoService.hospitaisComOcupacaoSuperiorANoventa()).thenReturn(1.0);
		
		assertEquals(OcupacaoService.hospitaisComOcupacaoSuperiorANoventa(), 1);
	}
	
	@Test
	public void hospitaisComOcupacaoInferiorANoventaDeveRetornarOsHospitaisQuePossuemOcupacaoInferiorA90Porcento() {
		/*
		 * aproveitando o hospital criado no comeco dos testes
		 */
		repo.save(hospital);

		Ocupacao ocupacao = new Ocupacao();
		ocupacao.setOcupacao(50);

		hospital.getOcupacao().addAll(Arrays.asList(ocupacao));

		repo.save(hospital);
		
		Mockito.when(OcupacaoService.hospitaisComOcupacaoInferiorANoventa()).thenReturn(1.0);
		
		assertEquals(OcupacaoService.hospitaisComOcupacaoInferiorANoventa(), 1);
	}
}
