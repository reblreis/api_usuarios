package br.com.cotiinformatica.application.dtos;

import java.util.Date;

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
public class ResponseCriarContaDTO {

	private Integer status;
	private String mensagem;
	private GetUsuarioDTO usuario;
	private Date dataHoraCadastro;
}
