package br.com.cotiinformatica.infra.producers;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private Queue queue;

	/*
	 * Método acessado para gravar um item na FILA PRODUCER / PUBLISHER -> Adicionar
	 * itens na fila
	 */
	public void send(String message) {
		rabbitTemplate.convertAndSend(this.queue.getName(), message);
	}

}