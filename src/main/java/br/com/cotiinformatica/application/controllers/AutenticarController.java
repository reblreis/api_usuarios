package br.com.cotiinformatica.application.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.application.dtos.PostAutenticarDTO;
import br.com.cotiinformatica.application.dtos.ResponseAutenticarDTO;
import br.com.cotiinformatica.domain.interfaces.IUsuarioDomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Autenticação de usuários")
@RestController
public class AutenticarController {

	@Autowired // injeção de dependência
	private IUsuarioDomainService usuarioDomainService;

	@CrossOrigin(origins = "*")
	@ApiOperation("ENDPOINT para autenticação de usuários e obtenção de Token.")
	@PostMapping("/api/autenticar")
	public ResponseEntity<ResponseAutenticarDTO> post(@Valid @RequestBody PostAutenticarDTO dto) {

		ResponseAutenticarDTO response = null;

		try {

			response = usuarioDomainService.autenticar(dto);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (IllegalArgumentException e) {

			response = new ResponseAutenticarDTO();
			response.setStatus(401); // UNAUTHORIZED (CLIENT ERROR)
			response.setMensagem(e.getMessage());

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {

			response = new ResponseAutenticarDTO();
			response.setStatus(500); // INTERNAL SERVER ERROR
			response.setMensagem(e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}