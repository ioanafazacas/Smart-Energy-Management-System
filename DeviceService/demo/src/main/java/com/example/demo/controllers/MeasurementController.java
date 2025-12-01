package com.example.demo.controllers;


import com.example.demo.dtos.DeviceMeasurementDTO;
import com.example.demo.messaging.MessageProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/measurements")
@Tag(name = "Measurement API", description = "Send device measurements to monitoring service")
public class MeasurementController {

    private final MessageProducer messageProducer;

    @Autowired
    public MeasurementController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Operation(summary = "Send a device measurement")
    @PostMapping
    public ResponseEntity<String> sendMeasurement(@Valid @RequestBody DeviceMeasurementDTO measurement) {
        try {
            messageProducer.sendMeasurement(measurement);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("Measurement sent to queue successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send measurement: " + e.getMessage());
        }
    }

    @Operation(summary = "Send a test measurement for a device")
    @PostMapping("/test/{deviceId}")
    public ResponseEntity<String> sendTestMeasurement(
            @PathVariable UUID deviceId,
            @RequestParam(defaultValue = "100.0") Double value) {

        DeviceMeasurementDTO measurement = new DeviceMeasurementDTO(
                LocalDateTime.now(),
                deviceId,
                value
        );

        try {
            messageProducer.sendMeasurement(measurement);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("Test measurement sent successfully for device: " + deviceId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send test measurement: " + e.getMessage());
        }
    }
}