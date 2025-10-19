package com.example.demo.services;

import com.example.demo.dtos.DeviceDTO;
import com.example.demo.dtos.ReadingDTO;
import com.example.demo.dtos.builders.DeviceBuilder;
import com.example.demo.dtos.builders.ReadingsBuilder;
import com.example.demo.entities.Device;
import com.example.demo.entities.DeviceReadings;
import com.example.demo.handlers.exceptions.model.ResourceNotFoundException;
import com.example.demo.repositories.ReadingsRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReadingsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadingsService.class);
    private final ReadingsRepository readingRepository;

    @Autowired
    public ReadingsService(ReadingsRepository readingRepository) {
        this.readingRepository = readingRepository;
    }

    //findAll
    public List<ReadingDTO> findreadings() {
        List<DeviceReadings> userList = readingRepository.findAll();
        return userList.stream()
                .map(ReadingsBuilder::toReadingDTO)
                .collect(Collectors.toList());
    }

    public ReadingDTO findReadingsById(UUID id) {
        Optional<DeviceReadings> prosumerOptional = readingRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("DeviceReadings with id {} was not found in db", id);
            throw new ResourceNotFoundException(DeviceReadings.class.getSimpleName() + " with id: " + id);
        }
        return ReadingsBuilder.toReadingDTO(prosumerOptional.get());
    }

    public List<ReadingDTO> findReadingsByDevice(Device device) {
        Optional<List<DeviceReadings>> prosumerOptional = readingRepository.findByDevice(device);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Device with deviec id {} was not found in db", device.getDeviceId());
            throw new ResourceNotFoundException(DeviceReadings.class.getSimpleName() + " with id: " + device.getDeviceId());
        }
        List<DeviceReadings> readings = prosumerOptional.get();
        return readings.stream()
                .map(ReadingsBuilder::toReadingDTO)
                .collect(Collectors.toList());
    }

    public UUID insert(ReadingDTO readingDTO) {
        DeviceReadings deviceReadings = ReadingsBuilder.toEntity(readingDTO);
        deviceReadings = readingRepository.save(deviceReadings);
        LOGGER.debug("Device with id {} was inserted in db", deviceReadings.getReadingId());
        return deviceReadings.getReadingId();
    }

    public void deleteById(UUID id) {
        if (!readingRepository.existsById(id)) {
            LOGGER.error("Devicereadings with id {} was not found in db", id);
            throw new ResourceNotFoundException(DeviceReadings.class.getSimpleName() + " with id: " + id);
        }

        readingRepository.deleteById(id);
        LOGGER.info("DeviceReadings with id {} has been deleted successfully", id);
    }

    @Transactional
    public void deleteByDevice(Device device){
        if (readingRepository.findByDevice(device).isPresent()) {
            LOGGER.error("Readings with device id {} was not found in db", device.getDeviceId());
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + device.getDeviceId());
        }
        readingRepository.deleteByDevice(device);
        LOGGER.info("Readings with device id {} has been deleted successfully", device.getDeviceId());
    }

    public ReadingDTO updateReadings(UUID id, ReadingDTO readingDTO) {
        Optional<DeviceReadings> readingsOptional = readingRepository.findById(id);

        if (readingsOptional.isEmpty()) {
            LOGGER.error("Readings with id {} not found", id);
            throw new ResourceNotFoundException("Readings with id: " + id + " not found");
        }

        DeviceReadings deviceReadings = readingsOptional.get();

        // Actualizam câmpurile
        if (readingDTO.getDeviceDTO() != null) deviceReadings.setDevice(DeviceBuilder.toEntity(readingDTO.getDeviceDTO()));
        if (readingDTO.getReadingTime() != null) deviceReadings.setReadingTime(readingDTO.getReadingTime());
        if (readingDTO.getConsumption() > -1) deviceReadings.setConsumption(readingDTO.getConsumption());


        DeviceReadings updatedReading = readingRepository.save(deviceReadings);

        LOGGER.info("Device with id {} was updated successfully", id);

        return ReadingsBuilder.toReadingDTO(updatedReading);
    }
}
