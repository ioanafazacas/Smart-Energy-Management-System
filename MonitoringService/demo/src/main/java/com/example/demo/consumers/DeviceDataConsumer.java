package com.example.demo.consumers;


import com.example.demo.dtos.DeviceMeasurementDTO;
import com.example.demo.services.MonitoringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DeviceDataConsumer {
    @Value("${INSTANCE_ID}")
    private String instanceId;

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceDataConsumer.class);

    private final MonitoringService monitoringService;
    private final RestTemplate restTemplate;

    @Autowired
    public DeviceDataConsumer(MonitoringService monitoringService, RestTemplate restTemplate) {
        this.monitoringService = monitoringService;
        this.restTemplate = restTemplate;
    }

    @RabbitListener(queues = "monitoring_ingest_${INSTANCE_ID}")
    //@RabbitListener(queues = "monitoring_ingest")
    public void consumeDeviceData(DeviceMeasurementDTO measurement) {
        System.out.println(
                "[MONITORING " + instanceId + "] Received measurement for device "
                        + measurement.getDeviceId()
        );
        try {
            LOGGER.info("📊 Received measurement: device={}, value={}, timestamp={}",
                    measurement.getDeviceId(),
                    measurement.getMeasurementValue(),
                    measurement.getTimestamp());
            System.out.println(
                    "[MONITORING] Consumed message from " +
                            System.getenv("INSTANCE_ID")
            );
            monitoringService.processMeasurement(measurement);

//            // 2️⃣ Trimite către WebSocketService
//            restTemplate.postForObject(
//                    "http://websocket-service:8085/push/measurement",
//                    measurement,
//                    Void.class
//            );

        } catch (Exception e) {
            LOGGER.error("❌ Error processing device measurement: {}", e.getMessage(), e);
        }
    }

}