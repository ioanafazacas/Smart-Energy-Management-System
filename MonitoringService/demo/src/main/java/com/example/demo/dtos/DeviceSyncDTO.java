package com.example.demo.dtos;

import java.io.Serializable;
import java.util.UUID;

public class DeviceSyncDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID deviceId;
    private String deviceName;
    private String description;
    private float maxConsumption;
    private UUID userId;
    private String operation; // CREATE, UPDATE, DELETE

    public DeviceSyncDTO() {
    }

    public DeviceSyncDTO(UUID deviceId, String deviceName, String description,
                         float maxConsumption, UUID userId, String operation) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.description = description;
        this.maxConsumption = maxConsumption;
        this.userId = userId;
        this.operation = operation;
    }

    // Getters and Setters
    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public float getMaxConsumption() {
        return maxConsumption;
    }

    public void setMaxConsumption(float maxConsumption) {
        this.maxConsumption = maxConsumption;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "DeviceSyncDTO{" +
                "deviceId=" + deviceId +
                ", deviceName='" + deviceName + '\'' +
                ", operation='" + operation + '\'' +
                ", maxConsumption=" + maxConsumption +
                '}';
    }
}