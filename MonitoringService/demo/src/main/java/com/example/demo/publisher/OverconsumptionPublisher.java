package com.example.demo.publisher;

import com.example.demo.config.RabbitMQConfig;
import com.example.demo.dtos.OverconsumptionNotificationDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OverconsumptionPublisher {

    private final RabbitTemplate rabbitTemplate;

    public OverconsumptionPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(UUID deviceId,
                        double value,
                        double max) {

        OverconsumptionNotificationDTO dto =
                new OverconsumptionNotificationDTO(
                        deviceId,
                        value,
                        max,
                        System.currentTimeMillis(),
                        "Device exceeded max consumption"
                );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.DEVICE_EXCHANGE,
                RabbitMQConfig.NOTIFICATION_ROUTING_KEY,
                dto
        );
    }
}
