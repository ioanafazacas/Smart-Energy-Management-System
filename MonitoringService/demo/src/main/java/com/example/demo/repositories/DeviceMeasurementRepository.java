package com.example.demo.repositories;


import com.example.demo.entities.DeviceMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface DeviceMeasurementRepository extends JpaRepository<DeviceMeasurement, UUID> {

    List<DeviceMeasurement> findByDeviceIdAndTimestampBetween(
            UUID deviceId, LocalDateTime start, LocalDateTime end);

    List<DeviceMeasurement> findByDeviceId(UUID deviceId);
}