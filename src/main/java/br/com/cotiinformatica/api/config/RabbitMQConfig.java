package br.com.cotiinformatica.api.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	/*
	 * Ler o nome da fila que será acessada mapeado no arquivo
	 * application.properties
	 */
	@Value("${queue.name}")
	private String queueName;

	/*
	 * Método para conexão na fila
	 */
	@Bean
	public Queue queue() {
		return new Queue(queueName, true);
	}
}