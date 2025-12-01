package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.device.measurement}")
    private String measurementQueueName;

    @Value("${rabbitmq.queue.device.sync}")
    private String syncQueueName;

    @Value("${rabbitmq.exchange.device}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key.measurement}")
    private String measurementRoutingKey;

    @Value("${rabbitmq.routing.key.sync}")
    private String syncRoutingKey;

    /**
     * Queue for device measurements
     */
    @Bean
    public Queue measurementQueue() {
        return QueueBuilder.durable(measurementQueueName)
                .withArgument("x-message-ttl", 86400000) // 24 hours TTL
                .build();
    }

    /**
     * Queue for device synchronization
     */
    @Bean
    public Queue syncQueue() {
        return QueueBuilder.durable(syncQueueName)
                .build();
    }

    /**
     * Topic Exchange for device events
     */
    @Bean
    public TopicExchange deviceExchange() {
        return new TopicExchange(exchangeName);
    }

    /**
     * Binding measurement queue to exchange
     */
    @Bean
    public Binding measurementBinding(Queue measurementQueue, TopicExchange deviceExchange) {
        return BindingBuilder
                .bind(measurementQueue)
                .to(deviceExchange)
                .with(measurementRoutingKey);
    }

    /**
     * Binding sync queue to exchange
     */
    @Bean
    public Binding syncBinding(Queue syncQueue, TopicExchange deviceExchange) {
        return BindingBuilder
                .bind(syncQueue)
                .to(deviceExchange)
                .with(syncRoutingKey);
    }

    /**
     * JSON Message Converter for serialization
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * RabbitTemplate with JSON converter
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}