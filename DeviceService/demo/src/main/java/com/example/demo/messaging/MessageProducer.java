package com.example.demo.messaging;

import com.example.demo.dtos.DeviceMeasurementDTO;
import com.example.demo.dtos.DeviceSyncDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class MessageProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.device}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key.measurement}")
    private String measurementRoutingKey;

    @Value("${rabbitmq.routing.key.sync}")
    private String syncRoutingKey;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Send device measurement to MonitoringService
     */
    public void sendMeasurement(DeviceMeasurementDTO measurement) {
        try {
            LOGGER.info("📤 Sending measurement to RabbitMQ: deviceId={}, value={}",
                    measurement.getDeviceId(), measurement.getMeasurementValue());

            rabbitTemplate.convertAndSend(exchangeName, measurementRoutingKey, measurement);

            LOGGER.info("✅ Measurement sent successfully");
        } catch (Exception e) {
            LOGGER.error("❌ Error sending measurement to RabbitMQ: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send measurement to queue", e);
        }
    }

    /**
     * Send device synchronization data
     */
    public void sendDeviceSync(DeviceSyncDTO deviceSync) {
        try {
            LOGGER.info("📤 Sending device sync to RabbitMQ: deviceId={}, operation={}",
                    deviceSync.getDeviceId(), deviceSync.getOperation());

            rabbitTemplate.convertAndSend(exchangeName, syncRoutingKey, deviceSync);

            LOGGER.info("✅ Device sync sent successfully");
        } catch (Exception e) {
            LOGGER.error("❌ Error sending device sync to RabbitMQ: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send device sync to queue", e);
        }
    }
}