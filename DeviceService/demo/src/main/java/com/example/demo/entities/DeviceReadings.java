package com.example.demo.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "device_readings")
public class DeviceReadings {
    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID readingId;

    @ManyToOne
    @JoinColumn(name = "device_id") // cheia străină în tabelul user
    private Device device;

    @Column(name = "readingTime", nullable = false)
    private LocalDateTime readingTime;

    @Column(name = "consumption", nullable = false)
    private float consumption;

    public DeviceReadings() {}

    public DeviceReadings(Device device, LocalDateTime readingTime, float consumption) {
        this.device = device;
        this.readingTime = readingTime;
        this.consumption = consumption;
    }

    public UUID getReadingId() {
        return readingId;
    }

    public void setReadingId(UUID readingId) {
        this.readingId = readingId;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public LocalDateTime getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(LocalDateTime readingTime) {
        this.readingTime = readingTime;
    }

    public float getConsumption() {
        return consumption;
    }

    public void setConsumption(float consumption) {
        this.consumption = consumption;
    }
}
