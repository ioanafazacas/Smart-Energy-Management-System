package com.example.demo.controllers;

import com.example.demo.dtos.DeviceDTO;
import com.example.demo.dtos.ReadingDTO;
import com.example.demo.dtos.builders.DeviceBuilder;
import com.example.demo.services.ReadingsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/device/readings")
@Validated
public class ReadingsController {

    private final ReadingsService readingsService;

    public ReadingsController(ReadingsService readingsService) {
        this.readingsService = readingsService;
    }

    @GetMapping
    public ResponseEntity<List<ReadingDTO>> getAllReadings() {
        return ResponseEntity.ok(readingsService.findreadings());
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody ReadingDTO readingDTO) {
        UUID id = readingsService.insert(readingDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build(); // 201 + Location header
    }

    @GetMapping("/device")
    public ResponseEntity<List<ReadingDTO>> getReadingsByDevice(@RequestBody DeviceDTO deviceDTO) {
        return ResponseEntity.ok(readingsService.findReadingsByDevice(DeviceBuilder.toEntity(deviceDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReading(@PathVariable UUID id) {
        readingsService.deleteById(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    @DeleteMapping("/device/")
    public ResponseEntity<Void> deleteReadingsByDevice(@RequestBody DeviceDTO deviceDTO) {
        readingsService.deleteByDevice(DeviceBuilder.toEntity(deviceDTO));
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReadingDTO> updateReading(@PathVariable UUID id,
                                                @RequestBody ReadingDTO readingDTO) {

        ReadingDTO updatedReading = readingsService.updateReadings(id, readingDTO);
        return ResponseEntity.ok(updatedReading);
    }
}
