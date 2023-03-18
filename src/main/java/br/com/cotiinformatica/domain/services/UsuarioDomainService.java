package br.com.cotiinformatica.domain.services;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.cotiinformatica.application.dtos.EmailMessageDTO;
import br.com.cotiinformatica.application.dtos.GetUsuarioDTO;
import br.com.cotiinformatica.application.dtos.PostAutenticarDTO;
import br.com.cotiinformatica.application.dtos.PostCriarContaDTO;
import br.com.cotiinformatica.application.dtos.PostRecuperarSenhaDTO;
import br.com.cotiinformatica.application.dtos.ResponseAutenticarDTO;
import br.com.cotiinformatica.application.dtos.ResponseCriarContaDTO;
import br.com.cotiinformatica.application.dtos.ResponseRecuperarSenhaDTO;
import br.com.cotiinformatica.domain.interfaces.IUsuarioDomainService;
import br.com.cotiinformatica.domain.models.Usuario;
import br.com.cotiinformatica.infra.components.MD5Component;
import br.com.cotiinformatica.infra.components.TokenComponent;
import br.com.cotiinformatica.infra.producers.MessageProducer;
import br.com.cotiinformatica.infra.repositories.IUsuarioRepository;

@Service
public class UsuarioDomainService implements IUsuarioDomainService {

	@Autowired // injeção de dependência
	private IUsuarioRepository usuarioRepository;

	@Autowired // injeção de dependência
	private MD5Component md5Component;

	@Autowired // injeção de dependência
	private TokenComponent tokenComponent;

	@Autowired // injeção de dependência
	private MessageProducer messageProducer;

	@Autowired // injeção de dependência
	private ObjectMapper objectMapper;

	@Override
	public ResponseAutenticarDTO autenticar(PostAutenticarDTO dto) {

		// procurar o usuário no banco de dados através do email e da senha
		Usuario usuario = usuarioRepository.findByEmailAndSenha(dto.getEmail(), md5Component.encrypt(dto.getSenha()));

		// verificar se o usuário não foi encontrado
		if (usuario == null)
			throw new IllegalArgumentException("Acesso negado. Usuário inválido.");

		// transferir os dados do usuário para o DTO
		ModelMapper modelMapper = new ModelMapper();
		GetUsuarioDTO usuarioDTO = modelMapper.map(usuario, GetUsuarioDTO.class);

		// gerando o token JWT para o usuário
		String token = null;
		try {
			token = tokenComponent.generateToken(usuario.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResponseAutenticarDTO response = new ResponseAutenticarDTO();
		response.setStatus(200);
		response.setMensagem("Usuário autenticado com sucesso.");
		response.setToken(token);
		response.setUsuario(usuarioDTO);

		return response;
	}

	@Override
	public ResponseCriarContaDTO criarConta(PostCriarContaDTO dto) {

		// verificar se já existe um usuário cadastrado com o email informado
		if (usuarioRepository.findByEmail(dto.getEmail()) != null)
			throw new IllegalArgumentException("O email informado já está cadastrado. Tente outro.");

		// transferir os dados do DTO para a classe de modelo de entidade
		ModelMapper modelMapper = new ModelMapper();
		Usuario usuario = modelMapper.map(dto, Usuario.class);

		usuario.setSenha(md5Component.encrypt(usuario.getSenha()));
		usuario.setDataHoraCriacao(new Date());

		// gravando no banco de dados
		usuarioRepository.save(usuario);

		// enviando mensagem de boas vindas
		EmailMessageDTO emailMessageDTO = new EmailMessageDTO();
		emailMessageDTO.setEmailTo(usuario.getEmail());
		emailMessageDTO.setSubject("Seja bem vindo a API de Usuários - COTI Informática");
		emailMessageDTO.setBody(
				"<div>" + "<p>Parabéns, " + usuario.getNome() + ". Sua conta de usuário foi criada com sucesso.</p>"
						+ "<p>Utilize o email e senha cadastrados para acessar sua conta."
						+ "<p>Att,<br/>Equipe COTI Informática</p>");

		// enviando a mensagem para a fila..
		try {
			messageProducer.send(objectMapper.writeValueAsString(emailMessageDTO));
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResponseCriarContaDTO response = new ResponseCriarContaDTO();
		response.setStatus(201);
		response.setMensagem("Usuário cadastrado com sucesso");
		response.setDataHoraCadastro(new Date());
		response.setUsuario(modelMapper.map(usuario, GetUsuarioDTO.class));

		return response;
	}

	@Override
	public ResponseRecuperarSenhaDTO recuperarSenha(PostRecuperarSenhaDTO dto) {

		// procurar o usuário no banco de dados através do email
		Usuario usuario = usuarioRepository.findByEmail(dto.getEmail());

		// verificar se o usuário foi encontrado
		if (usuario == null)
			throw new IllegalArgumentException("Email não encontrado. Usuário inválido.");

		// gerar uma nova senha para o usuário
		String novaSenha = new Faker().internet().password(8, 10, true);

		// enviando mensagem de recuperação de senha
		EmailMessageDTO emailMessageDTO = new EmailMessageDTO();
		emailMessageDTO.setEmailTo(usuario.getEmail());
		emailMessageDTO.setSubject("Recuperação de Senha (API de Usuários) - COTI Informática");
		emailMessageDTO.setBody("<div>" + "<p>Parabéns, " + usuario.getNome()
				+ ". Uma nova senha foi gerada com sucesso.</p>" + "<p>Utilize a senha <strong>" + novaSenha
				+ "</strong> para acessar sua conta." + "<p>Att,<br/>Equipe COTI Informática</p>");

		// enviando a mensagem para a fila..
		try {
			messageProducer.send(objectMapper.writeValueAsString(emailMessageDTO));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// atualizando a senha do usuário no banco de dados
		usuario.setSenha(md5Component.encrypt(novaSenha));
		usuarioRepository.save(usuario);

		ResponseRecuperarSenhaDTO response = new ResponseRecuperarSenhaDTO();
		response.setStatus(200);
		response.setMensagem("Recuperação de senha realizado com sucesso.");

		return response;
	}
}