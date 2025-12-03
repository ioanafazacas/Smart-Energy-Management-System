package com.example.demo.services;


import com.example.demo.dtos.DeviceSyncDTO;
import com.example.demo.entities.DeviceInfo;
import com.example.demo.repositories.DeviceInfoRepository;
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

    public void handleSyncEvent(DeviceSyncDTO event) {
        LOGGER.info("📩 Handling sync event: {}", event.getOperation());

        switch (event.getOperation()) {

            case "CREATE" -> handleDeviceCreated(event);
            case "DELETE" -> handleDeviceDeleted(event);

            default -> LOGGER.warn("⚠️ Unknown sync event: {}", event.getOperation());
        }
    }

    private void handleDeviceCreated(DeviceSyncDTO event) {
        LOGGER.info("🟢 Creating device locally for monitoring: {}", event.getDeviceId());

        DeviceInfo device = new DeviceInfo();
        device.setDeviceId(event.getDeviceId());
        device.setUserId(event.getUserId());
        device.setDeviceName(event.getDeviceName());
        device.setMaxConsumption(event.getMaxConsumption());

        deviceRepository.save(device);
    }

    private void handleDeviceDeleted(DeviceSyncDTO event) {
        LOGGER.info("🔴 Removing device from monitoring DB: {}", event.getDeviceId());
        deviceRepository.deleteById(event.getDeviceId());
    }

    private void handleUserCreated(DeviceSyncDTO event) {
        LOGGER.info("🟢 Received USER_CREATED — usually ignored by monitoring.");
        // optional: sync local user table
    }

    private void handleUserDeleted(DeviceSyncDTO event) {
        LOGGER.info("🔴 USER_DELETED event received — removing devices of user");
        deviceRepository.deleteByUserId(event.getUserId());
    }
}
