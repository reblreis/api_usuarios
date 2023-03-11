package br.com.cotiinformatica.application.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PostAutenticarDTO {

	@Email(message = "Por favor, informe um endereõ de email válido.")
	@NotBlank(message = "Por favor, informe o email de acesso do usuário.")
	private String email;
	
	@Size(min = 8, max = 20, message = "Informe a senha com 8 a 20 caracteres.")
	@NotBlank(message = "Por favor, informe a senha de acesso do usuário.")
	private String senha;

}