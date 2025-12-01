package com.example.demo.repositories;


import com.example.demo.entities.DeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeviceInfoRepository extends JpaRepository<DeviceInfo, UUID> {

    List<DeviceInfo> findByUserId(UUID userId);

    void deleteByUserId(UUID userId);
}