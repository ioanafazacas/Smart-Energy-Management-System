package com.example.demo.repositories;

import com.example.demo.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {

    Optional<List<Device>> findByUserId(UUID id);

    Optional<Device> findByDeviceId(UUID deviceId);

    void deleteByUserId(UUID userId);

    /*
     * Example: Custom query

    @Query(value = "SELECT p " +
            "FROM User p " +
            "WHERE p.name = :name " +
            "AND p.age >= 60  ")
    Optional<User> findSeniorsByName(@Param("name") String name);
*/
}
