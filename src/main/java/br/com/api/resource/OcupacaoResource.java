package br.com.api.resource;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.domain.Hospital;
import br.com.api.domain.Ocupacao;
import br.com.api.domain.dto.OcupacaoDTO;
import br.com.api.service.HospitalService;
import br.com.api.service.OcupacaoService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/ocupacao")
public class OcupacaoResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private OcupacaoService ocupacaoService;

	@Autowired
	private HospitalService hospitalService;

	/**
	 * retorna os hospitais e a média dos que possuem ocupação superior a 90%
	 */
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna os hospitais e a média daqueles que possuem ocupação superior a 90%"),
			@ApiResponse(code = 500, message = "Retorna um erro, Não possui hospital cadastrado."), })
	@GetMapping("/media-maior-90")
	public ResponseEntity<Double> listarMediaHospitalComOcupacaoSuperiorANoventa() {
		double media = this.ocupacaoService.hospitaisComOcupacaoSuperiorANoventa();

		return ResponseEntity.ok().body(media);
	}

	/**
	 * retorna os hospitais e a média dos que possuem ocupação inferior a 90%
	 */
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna os hospitais e a média daqueles que possuem ocupação inferior a 90%"),
			@ApiResponse(code = 500, message = "Retorna um erro, Não possui hospital cadastrado."), })
	@GetMapping("/media-menor-90")
	public ResponseEntity<Double> listarMediaHospitalComOcupacaoInferiorANoventa() {
		double media = this.ocupacaoService.hospitaisComOcupacaoInferiorANoventa();

		return ResponseEntity.ok().body(media);
	}

	/**
	 * Rota de insercao de ocupacao
	 */
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Adiciona a nova ocupacação do hospital e retorna a lista completa"),
			@ApiResponse(code = 400, message = "Retorna um erro. A ocupação foi inválida"), })
	@PutMapping("nova-ocupacao/{id}")
	public ResponseEntity<List<OcupacaoDTO>> atualizarOcupacao(@RequestBody Ocupacao ocupacao,
			@PathVariable Integer id) {
		Hospital hospital = this.hospitalService.buscarPorID(id);

		if (ocupacao.getOcupacao() < 0 || ocupacao.getOcupacao() > 100) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} else if (hospital != null) {
			/*
			 * Adiciona a nova ocupacao que irá conter esse hospital
			 */
			List<OcupacaoDTO> ocupacoesSalvas = this.hospitalService.atualizarOcupacao(id, ocupacao);
			return ResponseEntity.status(HttpStatus.OK).body(ocupacoesSalvas);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

}
