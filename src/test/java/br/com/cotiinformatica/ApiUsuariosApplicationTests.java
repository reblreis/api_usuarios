package br.com.cotiinformatica;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.cotiinformatica.application.dtos.PostAutenticarDTO;
import br.com.cotiinformatica.application.dtos.PostCriarContaDTO;
import br.com.cotiinformatica.application.dtos.PostRecuperarSenhaDTO;

@SpringBootTest
@AutoConfigureMockMvc
class ApiUsuariosApplicationTests {

	@Autowired
	private MockMvc mock;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void postCriarContaTest() throws Exception {

		PostCriarContaDTO dto = new PostCriarContaDTO();
		Faker faker = new Faker(new Locale("pt-BR"));

		dto.setNome("Usuário Testador");
		dto.setEmail(faker.internet().emailAddress());
		dto.setSenha("@Teste1234");

		mock.perform(
				post("/api/criar-conta").contentType("application/json").content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isCreated());
	}

	@Test
	public void postAutenticarTest() throws Exception {

		PostCriarContaDTO postCriarContaDTO = new PostCriarContaDTO();
		Faker faker = new Faker(new Locale("pt-BR"));

		postCriarContaDTO.setNome("Usuário Testador");
		postCriarContaDTO.setEmail(faker.internet().emailAddress());
		postCriarContaDTO.setSenha("@Teste1234");

		mock.perform(post("/api/criar-conta").contentType("application/json")
				.content(objectMapper.writeValueAsString(postCriarContaDTO))).andExpect(status().isCreated());

		PostAutenticarDTO dto = new PostAutenticarDTO();
		dto.setEmail(postCriarContaDTO.getEmail());
		dto.setSenha(postCriarContaDTO.getSenha());

		mock.perform(
				post("/api/autenticar").contentType("application/json").content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isOk());
	}

	@Test
	public void postRecuperarSenhaTest() throws Exception {

		PostCriarContaDTO postCriarContaDTO = new PostCriarContaDTO();
		Faker faker = new Faker(new Locale("pt-BR"));

		postCriarContaDTO.setNome("Usuário Testador");
		postCriarContaDTO.setEmail(faker.internet().emailAddress());
		postCriarContaDTO.setSenha("@Teste1234");

		mock.perform(post("/api/criar-conta").contentType("application/json")
				.content(objectMapper.writeValueAsString(postCriarContaDTO))).andExpect(status().isCreated());

		PostRecuperarSenhaDTO dto = new PostRecuperarSenhaDTO();
		dto.setEmail(postCriarContaDTO.getEmail());

		mock.perform(post("/api/recuperar-senha").contentType("application/json")
				.content(objectMapper.writeValueAsString(dto))).andExpect(status().isOk());
	}

}