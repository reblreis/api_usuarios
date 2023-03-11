package br.com.cotiinformatica.application.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
public class PostCriarContaDTO {

	@Pattern(regexp = "^[A-Za-zÀ-Üà-ü\\s]{6,150}$", message = "Por favor, informe um nome válido de 6 a 150 caracteres.")
	@NotBlank(message = "Por favor, informe o nome do usuário.")
	private String nome;
	
	@Email(message = "Por favor, informe um endereço de email válido.")
	@NotBlank(message = "Por favor, informe o email do usuário.")
	private String email;
	
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", 
			message = "Por favor, informe a senha com pelo menos 1 letra maiúscula, 1 letra minúscula, 1 número e 1 caractere especial.")
	@Size(min = 8, max = 20, message = "Informe a senha com 8 a 20 caracteres.")
	@NotBlank(message = "Por favor, informe a senha do usuário.")
	private String senha;
	
}