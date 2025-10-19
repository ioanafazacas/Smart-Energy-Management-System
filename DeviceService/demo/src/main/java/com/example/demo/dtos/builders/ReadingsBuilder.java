package com.example.demo.dtos.builders;

import com.example.demo.dtos.DeviceDTO;
import com.example.demo.dtos.ReadingDTO;
import com.example.demo.entities.Device;
import com.example.demo.entities.DeviceReadings;

public class ReadingsBuilder {
    public ReadingsBuilder(){}

    public static ReadingDTO toReadingDTO(DeviceReadings deviceReadings) {
        return new ReadingDTO(deviceReadings.getReadingId(),
                DeviceBuilder.toDeviceDTO(deviceReadings.getDevice()), deviceReadings.getReadingTime(), deviceReadings.getConsumption());
    }

    public static DeviceReadings toEntity(ReadingDTO readingDTO) {
        return new DeviceReadings(
                DeviceBuilder.toEntity(readingDTO.getDeviceDTO()),
                readingDTO.getReadingTime(),
                readingDTO.getConsumption());
    }
}
