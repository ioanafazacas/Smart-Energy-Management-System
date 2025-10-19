package com.example.demo.dtos.builders;

import com.example.demo.dtos.DeviceDTO;
import com.example.demo.entities.Device;

public class DeviceBuilder {

    private DeviceBuilder() {
    }

    public static DeviceDTO toDeviceDTO(Device device) {
        return new DeviceDTO(device.getDeviceId(), device.getUserId(), device.getSerialNumber(), device.getName(), device.getMaxConsumption());
    }

    public static Device toEntity(DeviceDTO deviceDTO) {
        return new Device(deviceDTO.getUserId(),
                deviceDTO.getSerialNumber(),
                deviceDTO.getName(),
                deviceDTO.getMaxConsumption());
    }
}
