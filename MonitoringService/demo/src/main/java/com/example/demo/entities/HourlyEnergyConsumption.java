package com.example.demo.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "hourly_energy_consumption")
public class HourlyEnergyConsumption {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @Column(name = "device_id", nullable = false)
    private UUID deviceId;

    @Column(name = "hour_timestamp", nullable = false)
    private LocalDateTime hourTimestamp;

    @Column(name = "total_consumption", nullable = false)
    private double totalConsumption;

    @Column(name = "max_consumption_threshold", nullable = false)
    private double maxConsumptionThreshold;

    @Column(name = "exceeded", nullable = false)
    private boolean exceeded;

    public HourlyEnergyConsumption() {}

    public HourlyEnergyConsumption(UUID deviceId, LocalDateTime hourTimestamp,
                                   double totalConsumption, double maxConsumptionThreshold) {
        this.deviceId = deviceId;
        this.hourTimestamp = hourTimestamp;
        this.totalConsumption = totalConsumption;
        this.maxConsumptionThreshold = maxConsumptionThreshold;
        this.exceeded = totalConsumption > maxConsumptionThreshold;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public LocalDateTime getHourTimestamp() {
        return hourTimestamp;
    }

    public void setHourTimestamp(LocalDateTime hourTimestamp) {
        this.hourTimestamp = hourTimestamp;
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public double getMaxConsumptionThreshold() {
        return maxConsumptionThreshold;
    }

    public void setMaxConsumptionThreshold(double maxConsumptionThreshold) {
        this.maxConsumptionThreshold = maxConsumptionThreshold;
    }

    public boolean isExceeded() {
        return exceeded;
    }

    public void setExceeded(boolean exceeded) {
        this.exceeded = exceeded;
    }
}