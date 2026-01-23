package com.example.demo.consumers;


import com.example.demo.dtos.OverconsumptionNotificationDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = "synchronization-notification-queue")
    public void consume(OverconsumptionNotificationDTO dto) {

        messagingTemplate.convertAndSend(
                "/topic/overconsumption",
                dto
        );
    }
}
