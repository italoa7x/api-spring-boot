package br.com.api.resource;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.domain.Hospital;
import br.com.api.domain.Ocupacao;
import br.com.api.domain.dto.OcupacaoDTO;
import br.com.api.service.HospitalService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/hospital")
public class HospitalResource implements Serializable {

	@Autowired
	private HospitalService hospitalService;

	private static final long serialVersionUID = 1L;

	/**
	 * Rota de criacao de hospital
	 */
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Retorna o hospital criado"),
			@ApiResponse(code = 400, message = "Não retorna nada, apenas o erro."), })
	@PostMapping
	public ResponseEntity<Hospital> salvarHospital(@Valid @RequestBody Hospital hospital) {
		Hospital hospitalSalvo = this.hospitalService.salvarHospital(hospital);

		if (hospitalSalvo != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(hospitalSalvo);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	/**
	 * Rota de listagem de hospital
	 */

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna todos os hospitais cadastrados"), })

	@GetMapping
	public List<Hospital> listarHospitais() {
		return this.hospitalService.listarHospitais();
	}

	/**
	 * Rota de insercao de ocupacao
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Adiciona a nova ocupacação do hospital e retorna a lista completa"),
			@ApiResponse(code = 400, message = "Retorna um erro. A ocupação foi inválida"), })
	@PutMapping("nova-ocupacao/{id}")
	public ResponseEntity<List<OcupacaoDTO>> atualizarOcupacao(@RequestBody Ocupacao ocupacao, @PathVariable Integer id) {
		Hospital hospital = this.hospitalService.buscarPorID(id);

		if (ocupacao.getOcupacao() < 0 || ocupacao.getOcupacao() > 100) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} else if (hospital != null) {
			/*
			 * Adiciona a nova ocupacao que irá conter esse hospital
			 */
			List<OcupacaoDTO> ocupacoesSalvas = this.hospitalService.atualizarOcupacao(id, ocupacao);
			return ResponseEntity.status(HttpStatus.OK).body(ocupacoesSalvas);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	/**
	 * Rota de buscar ultimo registro de ocupacao
	 */

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna a última ocupação registrada"),
			@ApiResponse(code = 400, message = "Retorna um erro. O código do hospital é inválido"), })
	@GetMapping("/ocupacoes/{id}")
	public ResponseEntity<OcupacaoDTO> buscarOcupacaoAtualizada(@PathVariable Integer id) {
		OcupacaoDTO ocupacao = this.hospitalService.buscarOcupacao(id);

		if (ocupacao == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} else {
			return ResponseEntity.ok(ocupacao);

		}
	}

	/**
	 * Rota de exclusao de hospital
	 */

	@ApiResponses(value = { @ApiResponse(code = 204, message = "Não retorna nada. O hospital foi excluido"),
			@ApiResponse(code = 500, message = "Retorna um erro. O código do hospital é inválido"), })
	@DeleteMapping("/{id}")
	public ResponseEntity<Hospital> excluirHospital(@PathVariable Integer id) {
		this.hospitalService.excluirHospital(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
}
