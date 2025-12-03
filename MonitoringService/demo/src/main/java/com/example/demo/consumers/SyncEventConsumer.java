package com.example.demo.consumers;


import com.example.demo.dtos.DeviceSyncDTO;
import com.example.demo.services.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SyncEventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SyncEventConsumer.class);

    private final SyncService syncService;

    @Autowired
    public SyncEventConsumer(SyncService syncService) {
        this.syncService = syncService;
    }

    @RabbitListener(queues = "device-sync-queue")
    public void consumeSyncEvent(DeviceSyncDTO event) {
        try {
            LOGGER.info("🔄 Received sync event: type={}, entityId={}",
                    event.getOperation(),
                    event.getDeviceId());

            syncService.handleSyncEvent(event);

        } catch (Exception e) {
            LOGGER.error("❌ Error processing sync event: {}", e.getMessage(), e);
        }
    }
}