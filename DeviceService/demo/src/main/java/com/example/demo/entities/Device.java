package com.example.demo.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "device")
public class Device {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID deviceId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "serialNumber", nullable = false)
    private String serialNumber;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "maxConsumption", nullable = false)
    private float maxConsumption;

    @OneToMany(mappedBy = "device")
    private List<DeviceReadings> readings;

    public Device() {}

    public Device(UUID userId, String serialNumber, String name, float maxConsumption) {
        this.userId = userId;
        this.serialNumber = serialNumber;
        this.name = name;
        this.maxConsumption = maxConsumption;
    }

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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public float getMaxConsumption() {
        return maxConsumption;
    }

    public void setMaxConsumption(float maxConsumption) {
        this.maxConsumption = maxConsumption;
    }
}
