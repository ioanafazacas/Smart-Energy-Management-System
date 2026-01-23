package com.example.demo.dtos;



import java.util.UUID;


public class OverconsumptionNotificationDTO {

    private UUID deviceId;
    private double measurementValue;
    private double maxConsumption;
    private long timestamp;
    private String message;


    public OverconsumptionNotificationDTO(UUID deviceId, double measurementValue, double maxConsumption, long timestamp, String message) {
        this.deviceId = deviceId;
        this.measurementValue = measurementValue;
        this.maxConsumption = maxConsumption;
        this.timestamp = timestamp;
        this.message = message;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public double getMeasurementValue() {
        return measurementValue;
    }

    public void setMeasurementValue(double measurementValue) {
        this.measurementValue = measurementValue;
    }

    public double getMaxConsumption() {
        return maxConsumption;
    }

    public void setMaxConsumption(double maxConsumption) {
        this.maxConsumption = maxConsumption;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
