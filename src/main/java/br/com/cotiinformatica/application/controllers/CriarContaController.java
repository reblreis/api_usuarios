package br.com.cotiinformatica.application.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.application.dtos.PostCriarContaDTO;
import br.com.cotiinformatica.application.dtos.ResponseCriarContaDTO;
import br.com.cotiinformatica.domain.interfaces.IUsuarioDomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Criação de conta de usuários")
@RestController
public class CriarContaController {

	@Autowired // injeção de dependência
	private IUsuarioDomainService usuarioDomainService;

	@CrossOrigin(origins = "*")
	@ApiOperation("ENDPOINT para cadastro de usuários.")
	@PostMapping("/api/criar-conta")
	public ResponseEntity<ResponseCriarContaDTO> post(@Valid @RequestBody PostCriarContaDTO dto) {

		ResponseCriarContaDTO response = null;

		try {

			response = usuarioDomainService.criarConta(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (IllegalArgumentException e) {

			response = new ResponseCriarContaDTO();
			response.setStatus(400); // BAD REQUEST (CLIENT ERROR)
			response.setMensagem(e.getMessage());

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {

			response = new ResponseCriarContaDTO();
			response.setStatus(500); // INTERNAL SERVER ERROR
			response.setMensagem(e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}