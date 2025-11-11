package com.example.demo.controllers;

import com.example.demo.dtos.DeviceDTO;
import com.example.demo.services.DeviceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/device")
@Validated
@Tag(name = "Device Management API", description = "Operații CRUD pentru dispozitivele conectate utilizatorilor")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        return ResponseEntity.ok(deviceService.findDevices());
    }

    @Operation(summary = "Creează un dispozitiv nou")
    @PostMapping("/create")
    public ResponseEntity<DeviceDTO> create(@Valid @RequestBody DeviceDTO deviceDTO) {
        UUID id = deviceService.insert(deviceDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        System.out.println(deviceDTO);
        DeviceDTO dto = deviceService.findDeviceById(id);
        return ResponseEntity.ok(dto); // 201 + Location header
    }

    @Operation(summary = "Obține un dispozitiv după ID")
    @GetMapping("/{id}")
    public ResponseEntity<DeviceDTO> getDevice(@PathVariable UUID id) {
        return ResponseEntity.ok(deviceService.findDeviceById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<DeviceDTO>> getDevicesByUser(@PathVariable UUID id) {
        return ResponseEntity.ok(deviceService.findDeviceByUser(id));
    }

    @Operation(summary = "Obține toate dispozitivele")
    @GetMapping("/all")
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        return ResponseEntity.ok(deviceService.findDevices());
    }

    @Operation(summary = "Șterge un dispozitiv")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable UUID id) {
        deviceService.deleteById(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    @Operation(summary = "Șterge toate dispozitivele unui utilizator")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteDevicesByUser(@PathVariable UUID id) {
        deviceService.deleteByUserId(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    @Operation(summary = "Actualizează un dispozitiv existent")
    @PutMapping("/{id}")
    public ResponseEntity<DeviceDTO> updateUser(@PathVariable UUID id,
                                                 @RequestBody DeviceDTO deviceDTO) {

            DeviceDTO updatedDevice = deviceService.updateDevice(id, deviceDTO);
            return ResponseEntity.ok(updatedDevice);
    }
}
