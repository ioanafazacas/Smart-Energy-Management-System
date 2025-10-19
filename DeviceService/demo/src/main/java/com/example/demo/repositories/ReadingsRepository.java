package com.example.demo.repositories;

import com.example.demo.entities.Device;
import com.example.demo.entities.DeviceReadings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadingsRepository extends JpaRepository<DeviceReadings, UUID> {
    Optional<List<DeviceReadings>> findByDevice(Device device);

    void deleteByDevice(Device device);
}
