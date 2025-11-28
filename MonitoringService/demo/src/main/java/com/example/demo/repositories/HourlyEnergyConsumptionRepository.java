package com.example.demo.repositories;

import com.example.demo.entities.HourlyEnergyConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HourlyEnergyConsumptionRepository extends JpaRepository<HourlyEnergyConsumption, UUID> {

    Optional<HourlyEnergyConsumption> findByDeviceIdAndHourTimestamp(UUID deviceId, LocalDateTime hourTimestamp);

    List<HourlyEnergyConsumption> findByDeviceId(UUID deviceId);

    List<HourlyEnergyConsumption> findByDeviceIdAndHourTimestampBetween(
            UUID deviceId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT h FROM HourlyEnergyConsumption h WHERE h.exceeded = true")
    List<HourlyEnergyConsumption> findAllExceeded();
}