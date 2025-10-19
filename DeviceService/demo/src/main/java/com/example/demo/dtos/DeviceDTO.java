package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Objects;
import java.util.UUID;

public class DeviceDTO {
    private UUID deviceId;
    @NotNull(message = "userId is required")
    private UUID userId;

    @NotBlank(message = "serialNumber is required")
    private String serialNumber;

    @NotBlank(message = "name is required")
    private String name;

    @Positive(message = "maxConsumption must be positive")
    private float maxConsumption;

    public DeviceDTO() {}

    public DeviceDTO(UUID user_id, String serialNumber,String name, float maxConsumption) {
        this.userId = user_id;
        this.serialNumber = serialNumber;
        this.name = name;
        this.maxConsumption = maxConsumption;
    }

    public DeviceDTO(UUID device_id, UUID user_id, String serialNumber,String name, float maxConsumption) {
        this.deviceId = device_id;
        this.userId = user_id;
        this.serialNumber = serialNumber;
        this.name = name;
        this.maxConsumption = maxConsumption;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID device_id) {
        this.deviceId = device_id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID user_id) {
        this.userId = user_id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public float getMaxConsumption() {
        return maxConsumption;
    }

    public void setMaxConsumption(float maxConsumption) {
        this.maxConsumption = maxConsumption;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDTO that = (DeviceDTO) o;
        return Objects.equals(serialNumber, that.serialNumber) && Objects.equals(name, that.name) && maxConsumption == that.maxConsumption && Objects.equals(userId, that.userId);
    }
    @Override public int hashCode() { return Objects.hash(serialNumber, maxConsumption, name,  deviceId); }
}
