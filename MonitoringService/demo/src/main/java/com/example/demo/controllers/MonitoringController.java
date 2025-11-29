package com.example.demo.controllers;

import com.example.demo.dtos.ConsumptionStatsDTO;
import com.example.demo.dtos.DeviceMeasurementDTO;
import com.example.demo.dtos.HourlyConsumptionDTO;
import com.example.demo.services.MonitoringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/monitoring")
@Validated
@Tag(name = "Monitoring API", description = "Monitor device energy consumption and analyze data")
public class MonitoringController {

    private final MonitoringService monitoringService;

    @Autowired
    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @Operation(summary = "Get all measurements for a device")
    @GetMapping("/device/{deviceId}/measurements")
    public ResponseEntity<List<DeviceMeasurementDTO>> getDeviceMeasurements(@PathVariable UUID deviceId) {
        List<DeviceMeasurementDTO> measurements = monitoringService.getDeviceMeasurements(deviceId);
        return ResponseEntity.ok(measurements);
    }

    @Operation(summary = "Get measurements for a device within a time range")
    @GetMapping("/device/{deviceId}/measurements/range")
    public ResponseEntity<List<DeviceMeasurementDTO>> getDeviceMeasurementsInRange(
            @PathVariable UUID deviceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        List<DeviceMeasurementDTO> measurements = monitoringService.getDeviceMeasurementsInRange(deviceId, start, end);
        return ResponseEntity.ok(measurements);
    }

    @Operation(summary = "Get hourly consumption for a device")
    @GetMapping("/device/{deviceId}/hourly")
    public ResponseEntity<List<HourlyConsumptionDTO>> getHourlyConsumption(@PathVariable UUID deviceId) {
        List<HourlyConsumptionDTO> consumption = monitoringService.getHourlyConsumption(deviceId);
        return ResponseEntity.ok(consumption);
    }

    @Operation(summary = "Get hourly consumption within a time range")
    @GetMapping("/device/{deviceId}/hourly/range")
    public ResponseEntity<List<HourlyConsumptionDTO>> getHourlyConsumptionInRange(
            @PathVariable UUID deviceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        List<HourlyConsumptionDTO> consumption = monitoringService.getHourlyConsumptionInRange(deviceId, start, end);
        return ResponseEntity.ok(consumption);
    }

    @Operation(summary = "Get all devices that exceeded max consumption")
    @GetMapping("/exceeded")
    public ResponseEntity<List<HourlyConsumptionDTO>> getExceededConsumption() {
        List<HourlyConsumptionDTO> exceeded = monitoringService.getAllExceededConsumption();
        return ResponseEntity.ok(exceeded);
    }

    @Operation(summary = "Get exceeded consumption for a specific device")
    @GetMapping("/device/{deviceId}/exceeded")
    public ResponseEntity<List<HourlyConsumptionDTO>> getDeviceExceededConsumption(@PathVariable UUID deviceId) {
        List<HourlyConsumptionDTO> exceeded = monitoringService.getDeviceExceededConsumption(deviceId);
        return ResponseEntity.ok(exceeded);
    }

    @Operation(summary = "Get consumption statistics for a device")
    @GetMapping("/device/{deviceId}/stats")
    public ResponseEntity<ConsumptionStatsDTO> getConsumptionStats(
            @PathVariable UUID deviceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        ConsumptionStatsDTO stats = monitoringService.getConsumptionStats(deviceId, start, end);
        if (stats == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stats);
    }

    @Operation(summary = "Manually process a new measurement")
    @PostMapping("/measurement")
    public ResponseEntity<String> processMeasurement(@Valid @RequestBody DeviceMeasurementDTO measurement) {
        monitoringService.processMeasurement(measurement);
        return ResponseEntity.ok("Measurement processed successfully");
    }

    @Operation(summary = "Get all hourly consumption records")
    @GetMapping("/hourly/all")
    public ResponseEntity<List<HourlyConsumptionDTO>> getAllHourlyConsumption() {
        List<HourlyConsumptionDTO> consumption = monitoringService.getAllHourlyConsumption();
        return ResponseEntity.ok(consumption);
    }

    @Operation(summary = "Health check endpoint")
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Monitoring Service is running");
    }
}