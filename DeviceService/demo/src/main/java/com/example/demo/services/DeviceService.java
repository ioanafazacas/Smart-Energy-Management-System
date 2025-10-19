package com.example.demo.services;


import com.example.demo.dtos.DeviceDTO;
import com.example.demo.dtos.builders.DeviceBuilder;
import com.example.demo.entities.Device;
import com.example.demo.handlers.exceptions.model.ResourceNotFoundException;
import com.example.demo.repositories.DeviceRepository;
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
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    //findAll
    public List<DeviceDTO> findDevices() {
        List<Device> userList = deviceRepository.findAll();
        return userList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public DeviceDTO findDeviceById(UUID id) {
        Optional<Device> prosumerOptional = deviceRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        return DeviceBuilder.toDeviceDTO(prosumerOptional.get());
    }

    public List<DeviceDTO> findDeviceByUser(UUID id) {
        Optional<List<Device>> prosumerOptional = deviceRepository.findByUserId(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Device with user id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        List<Device> deviceList = prosumerOptional.get();
        return deviceList.stream()
                        .map(DeviceBuilder::toDeviceDTO)
                        .collect((Collectors.toList()));
    }

    public UUID insert(DeviceDTO deviceDTO) {
        Device device = DeviceBuilder.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        LOGGER.debug("Device with id {} was inserted in db", device.getDeviceId());
        return device.getDeviceId();
    }

    public void deleteById(UUID id) {
        if (!deviceRepository.existsById(id)) {
            LOGGER.error("Device with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }

        deviceRepository.deleteById(id);
        LOGGER.info("Device with id {} has been deleted successfully", id);
    }

    @Transactional
    public void deleteByUserId(UUID id){
        if (deviceRepository.findByDeviceId(id).isPresent()) {
            LOGGER.error("Device with user id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        deviceRepository.deleteByUserId(id);
        LOGGER.info("Device with user id {} has been deleted successfully", id);
    }

    public DeviceDTO updateDevice(UUID id, DeviceDTO deviceDTO) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);

        if (deviceOptional.isEmpty()) {
            LOGGER.error("Device with id {} not found", id);
            throw new ResourceNotFoundException("Device with id: " + id + " not found");
        }

        Device device = deviceOptional.get();

        // Actualizam câmpurile
        if (deviceDTO.getUserId() != null) device.setUserId(deviceDTO.getUserId());
        if (deviceDTO.getName() != null) device.setName(deviceDTO.getName());
        if (deviceDTO.getSerialNumber() != null) device.setSerialNumber(deviceDTO.getSerialNumber());
        if (deviceDTO.getMaxConsumption() > -1) device.setMaxConsumption(deviceDTO.getMaxConsumption());


        Device updatedDevice = deviceRepository.save(device);

        LOGGER.info("Device with id {} was updated successfully", id);

        return DeviceBuilder.toDeviceDTO(updatedDevice);
    }

}
