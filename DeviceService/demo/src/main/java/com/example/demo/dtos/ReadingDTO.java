package com.example.demo.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class ReadingDTO {

    private UUID readingId;

    @NotNull(message = "device is required")
    private DeviceDTO deviceDTO;
    @NotBlank(message = "readingTime is required")
    private LocalDateTime readingTime;
    @Positive(message = "consumption must be positive")
    private float consumption;

    public ReadingDTO() {
    }

    public ReadingDTO(DeviceDTO deviceDTO, LocalDateTime readingTime, float consumption) {
        this.deviceDTO = deviceDTO;
        this.readingTime = readingTime;
        this.consumption = consumption;
    }

    public ReadingDTO(UUID readingId, DeviceDTO deviceDTO, LocalDateTime readingTime, float consumption) {
        this.readingId = readingId;
        this.deviceDTO = deviceDTO;
        this.readingTime = readingTime;
        this.consumption = consumption;
    }

    public UUID getReadingId() {
        return readingId;
    }

    public void setReadingId(UUID readingId) {
        this.readingId = readingId;
    }

    public DeviceDTO getDeviceDTO() {
        return deviceDTO;
    }

    public void setDeviceDTO(DeviceDTO deviceDTO) {
        this.deviceDTO = deviceDTO;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReadingDTO that = (ReadingDTO) o;
        return Objects.equals(deviceDTO, that.deviceDTO) &&
                Objects.equals(readingTime, that.readingTime) &&
                consumption == that.consumption;
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceDTO, readingTime, consumption);
    }
}
