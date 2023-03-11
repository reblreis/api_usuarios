package br.com.cotiinformatica.domain.interfaces;

import br.com.cotiinformatica.application.dtos.PostAutenticarDTO;
import br.com.cotiinformatica.application.dtos.PostCriarContaDTO;
import br.com.cotiinformatica.application.dtos.PostRecuperarSenhaDTO;
import br.com.cotiinformatica.application.dtos.ResponseAutenticarDTO;
import br.com.cotiinformatica.application.dtos.ResponseCriarContaDTO;
import br.com.cotiinformatica.application.dtos.ResponseRecuperarSenhaDTO;

public interface IUsuarioDomainService {

	// Método para implementar as regras de negócio para
	// autenticação do usuário
	ResponseAutenticarDTO autenticar(PostAutenticarDTO dto);

	// Método para implementar as regras de negócio para
	// criação da conta do usuário
	ResponseCriarContaDTO criarConta(PostCriarContaDTO dto);

	// Método pata implementar as regras de negócio para
	// recuperação da senha do usuário
	ResponseRecuperarSenhaDTO recuperarSenha(PostRecuperarSenhaDTO dto);
}