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
    @Value("${INSTANCE_ID}")
    private String instanceId;

    // Queue names

    //public static final String SYNC_QUEUE = "synchronization.queue";

    // Exchange names

    public static final String SYNC_EXCHANGE = "synchronization.exchange";

    // Routing keys

    public static final String SYNC_ROUTING_KEY = "sync.event";

    public static final String DEVICE_EXCHANGE = "device-exchange";
    public static final String NOTIFICATION_QUEUE = "synchronization-notification-queue";
    public static final String NOTIFICATION_ROUTING_KEY = "device.notification.overconsumption";


    @Bean
    public Queue monitoringIngestQueue() {
        String queueName = "monitoring_ingest_" + instanceId;
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Queue deviceSyncQueue() {
        return new Queue("device-sync-queue", true);
    }

    @Bean
    public Queue syncQueue() {
        return QueueBuilder.durable("device-sync-queue").build();
    }
    // Synchronization Queue Configuration
//    @Bean
//    public Queue syncQueue() {
//        return QueueBuilder.durable(SYNC_QUEUE)
//                .build();
//    }

    @Bean
    public TopicExchange deviceExchange() {
        return new TopicExchange(DEVICE_EXCHANGE, true, false);
    }

    @Bean
    public Queue notificationQueue() {
        return QueueBuilder.durable(NOTIFICATION_QUEUE).build();
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(deviceExchange())
                .with(NOTIFICATION_ROUTING_KEY);
    }

    @Bean
    public TopicExchange syncExchange() {
        return new TopicExchange(SYNC_EXCHANGE);
    }

    @Bean
    public Binding syncBinding() {
        return BindingBuilder
                .bind(syncQueue())
                .to(syncExchange())
                .with(SYNC_ROUTING_KEY);
    }

    // Message Converter
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}