package br.com.cotiinformatica.infra.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cotiinformatica.application.dtos.EmailMessageDTO;
import br.com.cotiinformatica.infra.components.EmailComponent;

@Service
public class MessageConsumer {

	@Autowired // Injeção de dependência
	EmailComponent emailComponent;

	@Autowired // Injeção de dependência
	ObjectMapper objectMapper;

	/*
	 * Método para ler e processar a fila
	 */
	@RabbitListener(queues = { "${queue.name}" })
	public void receive(@Payload String payload) throws Exception {

		// lendo o conteudo da mensagem gravada na fila do RabbitMQ
		EmailMessageDTO emailMessageDTO = objectMapper.readValue(payload, EmailMessageDTO.class);

		// enviar o email
		emailComponent.sendMessage(emailMessageDTO.getEmailTo(), emailMessageDTO.getSubject(),
				emailMessageDTO.getBody());
	}
}