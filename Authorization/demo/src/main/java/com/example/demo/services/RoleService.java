package com.example.demo.services;

import com.example.demo.dtos.RoleDTO;
import com.example.demo.dtos.builders.RoleBuilder;
import com.example.demo.entities.Role;
import com.example.demo.handlers.exceptions.model.ResourceNotFoundException;
import com.example.demo.repositories.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    //findAll
    public List<RoleDTO> findRoles() {
        List<Role> roleList = roleRepository.findAll();
        return roleList.stream()
                .map(RoleBuilder::toRoleDTO)
                .collect(Collectors.toList());
    }

    public RoleDTO findRoleById(int id) {
        Optional<Role> prosumerOptional = roleRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Role with id {} was not found in db", id);
            throw new ResourceNotFoundException(Role.class.getSimpleName() + " with id: " + id);
        }
        return RoleBuilder.toRoleDTO(prosumerOptional.get());
    }

    public int insert(RoleDTO roleDTO) {
        Role role = RoleBuilder.toEntity(roleDTO);
        role = roleRepository.save(role);
        LOGGER.debug("Role with id {} was inserted in db", role.getRole_id());
        return role.getRole_id();
    }

    public void deleteById(int id) {
        if (!roleRepository.existsById(id)) {
            LOGGER.error("Role with id {} was not found in db", id);
            throw new ResourceNotFoundException(Role.class.getSimpleName() + " with id: " + id);
        }

        roleRepository.deleteById(id);
        LOGGER.info("Role with id {} has been deleted successfully", id);
    }

    public RoleDTO updateRole(int id, RoleDTO roleDTO) {
        Optional<Role> roleOptional = roleRepository.findById(id);

        if (roleOptional.isEmpty()) {
            LOGGER.error("Role with id {} not found", id);
            throw new ResourceNotFoundException("Role with id: " + id + " not found");
        }

        Role role = roleOptional.get();

        // Actualizam câmpurile
        if (roleDTO.getRole_name() != null) role.setRole_name(roleDTO.getRole_name());

        Role updateRole = roleRepository.save(role);

        LOGGER.info("Role with id {} was updated successfully", id);

        return RoleBuilder.toRoleDTO(updateRole);
    }

}