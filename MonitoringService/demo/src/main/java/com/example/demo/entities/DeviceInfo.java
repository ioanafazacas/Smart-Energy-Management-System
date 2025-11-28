package com.example.demo.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "device_info")
public class DeviceInfo {

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID deviceId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "max_consumption", nullable = false)
    private double maxConsumption;

    @Column(name = "device_name")
    private String deviceName;

    public DeviceInfo() {}

    public DeviceInfo(UUID deviceId, UUID userId, double maxConsumption, String deviceName) {
        this.deviceId = deviceId;
        this.userId = userId;
        this.maxConsumption = maxConsumption;
        this.deviceName = deviceName;
    }

    // Getters and Setters
    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public double getMaxConsumption() {
        return maxConsumption;
    }

    public void setMaxConsumption(double maxConsumption) {
        this.maxConsumption = maxConsumption;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}