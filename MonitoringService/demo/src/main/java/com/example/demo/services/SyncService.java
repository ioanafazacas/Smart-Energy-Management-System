package com.example.demo.services;


import com.example.demo.dtos.SyncEventDTO;
import com.example.demo.entities.DeviceInfo;
import com.example.demo.repositories.DeviceInfoRepository;
import com.example.demo.services.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SyncService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SyncService.class);

    private final DeviceInfoRepository deviceRepository;

    public SyncService(DeviceInfoRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public void handleSyncEvent(SyncEventDTO event) {
        LOGGER.info("📩 Handling sync event: {}", event.getEventType());

        switch (event.getEventType()) {

            case "DEVICE_CREATED" -> handleDeviceCreated(event);
            case "DEVICE_DELETED" -> handleDeviceDeleted(event);

            // Optional depending on your architecture
            case "USER_CREATED" -> handleUserCreated(event);
            case "USER_DELETED" -> handleUserDeleted(event);

            default -> LOGGER.warn("⚠️ Unknown sync event: {}", event.getEventType());
        }
    }

    private void handleDeviceCreated(SyncEventDTO event) {
        LOGGER.info("🟢 Creating device locally for monitoring: {}", event.getEntityId());

        DeviceInfo device = new DeviceInfo();
        device.setDeviceId(event.getEntityId());
        device.setUserId(event.getUserId());
        device.setDeviceName(event.getDeviceName());
        device.setMaxConsumption(event.getMaxConsumption());

        deviceRepository.save(device);
    }

    private void handleDeviceDeleted(SyncEventDTO event) {
        LOGGER.info("🔴 Removing device from monitoring DB: {}", event.getEntityId());
        deviceRepository.deleteById(event.getEntityId());
    }

    private void handleUserCreated(SyncEventDTO event) {
        LOGGER.info("🟢 Received USER_CREATED — usually ignored by monitoring.");
        // optional: sync local user table
    }

    private void handleUserDeleted(SyncEventDTO event) {
        LOGGER.info("🔴 USER_DELETED event received — removing devices of user");
        deviceRepository.deleteByUserId(event.getUserId());
    }
}
