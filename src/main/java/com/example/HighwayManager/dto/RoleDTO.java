package com.example.HighwayManager.dto;

import com.example.HighwayManager.model.Role;
import lombok.Data;

@Data
public class RoleDTO {
    private Long id;
    private String name;

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
