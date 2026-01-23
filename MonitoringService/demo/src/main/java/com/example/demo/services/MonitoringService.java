package com.example.demo.services;

import com.example.demo.dtos.ConsumptionStatsDTO;
import com.example.demo.dtos.DeviceMeasurementDTO;
import com.example.demo.dtos.HourlyConsumptionDTO;
import com.example.demo.entities.DeviceInfo;
import com.example.demo.entities.DeviceMeasurement;
import com.example.demo.entities.HourlyEnergyConsumption;
import com.example.demo.publisher.OverconsumptionPublisher;
import com.example.demo.repositories.DeviceInfoRepository;
import com.example.demo.repositories.DeviceMeasurementRepository;
import com.example.demo.repositories.HourlyEnergyConsumptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MonitoringService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringService.class);

    private final DeviceMeasurementRepository measurementRepository;
    private final HourlyEnergyConsumptionRepository hourlyConsumptionRepository;
    private final DeviceInfoRepository deviceInfoRepository;
    private final OverconsumptionPublisher overconsumptionPublisher;

    @Autowired
    public MonitoringService(DeviceMeasurementRepository measurementRepository,
                             HourlyEnergyConsumptionRepository hourlyConsumptionRepository,
                             DeviceInfoRepository deviceInfoRepository, OverconsumptionPublisher overconsumptionPublisher) {
        this.measurementRepository = measurementRepository;
        this.hourlyConsumptionRepository = hourlyConsumptionRepository;
        this.deviceInfoRepository = deviceInfoRepository;
        this.overconsumptionPublisher = overconsumptionPublisher;
    }

    @Transactional
    public void processMeasurement(DeviceMeasurementDTO dto) {
        // Save raw measurement
        LocalDateTime localTimestamp =
                Instant.ofEpochMilli(dto.getTimestamp())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();

        LocalDateTime hourTimestamp =
                localTimestamp.truncatedTo(ChronoUnit.HOURS);
        DeviceMeasurement measurement = new DeviceMeasurement(
                dto.getDeviceId(),
                localTimestamp,
                dto.getMeasurementValue()
        );
        measurementRepository.save(measurement);

        // Get device info for max consumption threshold
        Optional<DeviceInfo> deviceInfoOpt = deviceInfoRepository.findById(dto.getDeviceId());
        if (deviceInfoOpt.isEmpty()) {
            LOGGER.warn("⚠️ Device info not found for device: {}", dto.getDeviceId());
            return;
        }

        DeviceInfo deviceInfo = deviceInfoOpt.get();

        // Calculate hourly consumption

        updateHourlyConsumption(dto.getDeviceId(), hourTimestamp,
                dto.getMeasurementValue(), deviceInfo.getMaxConsumption());
    }

    @Transactional
    public void updateHourlyConsumption(UUID deviceId, LocalDateTime hourTimestamp,
                                        double measurementValue, double maxConsumption) {
        Optional<HourlyEnergyConsumption> existingOpt =
                hourlyConsumptionRepository.findByDeviceIdAndHourTimestamp(deviceId, hourTimestamp);

        HourlyEnergyConsumption hourly;

        if (existingOpt.isPresent()) {
            // Update existing record
            hourly = existingOpt.get();
            hourly.setTotalConsumption(hourly.getTotalConsumption() + measurementValue);
            hourly.setExceeded(hourly.getTotalConsumption() > maxConsumption);

            if (hourly.getTotalConsumption() > maxConsumption) {
                overconsumptionPublisher.publish(
                        deviceId,
                        hourly.getTotalConsumption(),
                        maxConsumption
                );
            }


            LOGGER.info("📈 Updated hourly consumption for device {} at {}: total={}",
                    deviceId, hourTimestamp, hourly.getTotalConsumption());
        } else {
            // Create new record
            hourly = new HourlyEnergyConsumption(deviceId, hourTimestamp,
                    measurementValue, maxConsumption);
            LOGGER.info("✨ Created new hourly consumption for device {} at {}: total={}",
                    deviceId, hourTimestamp, hourly.getTotalConsumption());
        }

        hourlyConsumptionRepository.save(hourly);
    }

    // Query methods for controller
    public List<DeviceMeasurementDTO> getDeviceMeasurements(UUID deviceId) {
        List<DeviceMeasurement> measurements = measurementRepository.findByDeviceId(deviceId);
        return measurements.stream()
                .map(this::toDeviceMeasurementDTO)
                .collect(Collectors.toList());
    }

    public List<DeviceMeasurementDTO> getDeviceMeasurementsInRange(UUID deviceId, LocalDateTime start, LocalDateTime end) {
        List<DeviceMeasurement> measurements = measurementRepository.findByDeviceIdAndTimestampBetween(deviceId, start, end);
        return measurements.stream()
                .map(this::toDeviceMeasurementDTO)
                .collect(Collectors.toList());
    }

    public List<HourlyConsumptionDTO> getHourlyConsumption(UUID deviceId) {
        List<HourlyEnergyConsumption> consumption = hourlyConsumptionRepository.findByDeviceId(deviceId);
        return consumption.stream()
                .map(this::toHourlyConsumptionDTO)
                .collect(Collectors.toList());
    }

    public List<HourlyConsumptionDTO> getHourlyConsumptionInRange(UUID deviceId, LocalDateTime start, LocalDateTime end) {
        List<HourlyEnergyConsumption> consumption = hourlyConsumptionRepository.findByDeviceIdAndHourTimestampBetween(deviceId, start, end);
        return consumption.stream()
                .map(this::toHourlyConsumptionDTO)
                .collect(Collectors.toList());
    }

    public List<HourlyConsumptionDTO> getAllExceededConsumption() {
        List<HourlyEnergyConsumption> exceeded = hourlyConsumptionRepository.findAllExceeded();
        return exceeded.stream()
                .map(this::toHourlyConsumptionDTO)
                .collect(Collectors.toList());
    }

    public List<HourlyConsumptionDTO> getDeviceExceededConsumption(UUID deviceId) {
        List<HourlyEnergyConsumption> all = hourlyConsumptionRepository.findByDeviceId(deviceId);
        return all.stream()
                .filter(HourlyEnergyConsumption::isExceeded)
                .map(this::toHourlyConsumptionDTO)
                .collect(Collectors.toList());
    }

    public List<HourlyConsumptionDTO> getAllHourlyConsumption() {
        List<HourlyEnergyConsumption> all = hourlyConsumptionRepository.findAll();
        return all.stream()
                .map(this::toHourlyConsumptionDTO)
                .collect(Collectors.toList());
    }

    public ConsumptionStatsDTO getConsumptionStats(UUID deviceId, LocalDateTime start, LocalDateTime end) {
        List<HourlyEnergyConsumption> consumptions = hourlyConsumptionRepository.findByDeviceIdAndHourTimestampBetween(deviceId, start, end);

        if (consumptions.isEmpty()) {
            return null;
        }

        double totalConsumption = consumptions.stream()
                .mapToDouble(HourlyEnergyConsumption::getTotalConsumption)
                .sum();

        double avgConsumption = totalConsumption / consumptions.size();

        double maxConsumption = consumptions.stream()
                .mapToDouble(HourlyEnergyConsumption::getTotalConsumption)
                .max()
                .orElse(0);

        double minConsumption = consumptions.stream()
                .mapToDouble(HourlyEnergyConsumption::getTotalConsumption)
                .min()
                .orElse(0);

        long exceededCount = consumptions.stream()
                .filter(HourlyEnergyConsumption::isExceeded)
                .count();

        return new ConsumptionStatsDTO(deviceId, totalConsumption, avgConsumption,
                maxConsumption, minConsumption, exceededCount, consumptions.size());
    }

    // Helper methods to convert entities to DTOs
    private DeviceMeasurementDTO toDeviceMeasurementDTO(DeviceMeasurement measurement) {
// conversie LocalDateTime → long epoch millis
        long timestampMillis = measurement.getTimestamp()
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        return new DeviceMeasurementDTO(
                timestampMillis,
                measurement.getDeviceId(),
                measurement.getMeasurementValue()
        );
    }

    private HourlyConsumptionDTO toHourlyConsumptionDTO(HourlyEnergyConsumption consumption) {
        return new HourlyConsumptionDTO(
                consumption.getId(),
                consumption.getDeviceId(),
                consumption.getHourTimestamp(),
                consumption.getTotalConsumption(),
                consumption.getMaxConsumptionThreshold(),
                consumption.isExceeded()
        );
    }

    public List<HourlyConsumptionDTO> getUserHourlyConsumptionInRange(UUID userId, LocalDateTime start, LocalDateTime end) {
        List<DeviceInfo> userDevices = deviceInfoRepository.findByUserId(userId);

        return userDevices.stream()
                .flatMap(device -> {
                    List<HourlyEnergyConsumption> consumption =
                            hourlyConsumptionRepository.findByDeviceIdAndHourTimestampBetween(
                                    device.getDeviceId(), start, end
                            );
                    return consumption.stream();
                })
                .map(this::toHourlyConsumptionDTO)
                .collect(Collectors.toList());
    }
}