package br.com.cotiinformatica.application.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.application.dtos.PostRecuperarSenhaDTO;
import br.com.cotiinformatica.application.dtos.ResponseRecuperarSenhaDTO;
import br.com.cotiinformatica.domain.interfaces.IUsuarioDomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Recuperação de senha")
@RestController
public class RecuperarSenhaController {
	
	@Autowired
	private IUsuarioDomainService usuarioDomainService;

	@ApiOperation("ENDPOINT para recuperação da senha de acesso do usuário.")
	@PostMapping("/api/recuperar-senha")
	public ResponseEntity<ResponseRecuperarSenhaDTO> post(@Valid @RequestBody PostRecuperarSenhaDTO dto) {

		ResponseRecuperarSenhaDTO response = null;
		
		try {
		
			response = usuarioDomainService.recuperarSenha(dto);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		catch(IllegalArgumentException e) {
			
			response = new ResponseRecuperarSenhaDTO();
			response.setStatus(400); //BAD REQUEST (CLIENT ERROR)
			response.setMensagem(e.getMessage());
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);			
		}
		catch(Exception e) {
			
			response = new ResponseRecuperarSenhaDTO();
			response.setStatus(500); //INTERNAL SERVER ERROR
			response.setMensagem(e.getMessage());
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}		
	}

}