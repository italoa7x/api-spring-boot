package br.com.api.resource;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.domain.MediaOcupacao;
import br.com.api.service.OcupacaoService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/ocupacao")
public class OcupacaoResource implements Serializable{

	private static final long serialVersionUID = 1L;

	@Autowired
	private OcupacaoService ocupacaoService;
	
	/**
	 * retorna os hospitais e a média dos que possuem ocupação superior a 90%
	 */
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Retorna os hospitais e a média daqueles que possuem ocupação superior a 90%"),
			@ApiResponse(code = 500, message = "Retorna um erro, Não possui hospital cadastrado."), })
	@GetMapping("/media-maior-90")
	public ResponseEntity<MediaOcupacao> listarMediaHospitalComOcupacaoSuperiorANoventa(){
		MediaOcupacao media = this.ocupacaoService.hospitaisComOcupacaoSuperiorANoventa();
		
		return ResponseEntity.ok().body(media);
	}
	/**
	 * retorna os hospitais e a média dos que possuem ocupação inferior a 90%
	 */
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Retorna os hospitais e a média daqueles que possuem ocupação inferior a 90%"),
			@ApiResponse(code = 500, message = "Retorna um erro, Não possui hospital cadastrado."), })
	@GetMapping("/media-menor-90")
	public ResponseEntity<MediaOcupacao> listarMediaHospitalComOcupacaoInferiorANoventa(){
		MediaOcupacao media = this.ocupacaoService.hospitaisComOcupacaoInferiorANoventa();
		
		return ResponseEntity.ok().body(media);
	}
}
