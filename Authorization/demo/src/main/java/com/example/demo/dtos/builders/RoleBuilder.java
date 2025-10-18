package com.example.demo.dtos.builders;

import com.example.demo.dtos.RoleDTO;
import com.example.demo.entities.Role;


public class RoleBuilder {

    private RoleBuilder() {
    }

    public static RoleDTO toRoleDTO(Role role) {
        return new RoleDTO(role.getRole_id(), role.getRole_name());
    }

    public static Role toEntity(RoleDTO roleDTO) {
        return new Role(roleDTO.getRole_id(), roleDTO.getRole_name());

    }
}
