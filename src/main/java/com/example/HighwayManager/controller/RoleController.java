package com.example.HighwayManager.controller;

import com.example.HighwayManager.dto.RoleCreationDTO;
import com.example.HighwayManager.dto.RoleDTO;
import com.example.HighwayManager.exception.IllegalArgumentException;
import com.example.HighwayManager.model.Role;
import com.example.HighwayManager.service.RoleService;
import com.example.HighwayManager.exception.IllegalStateException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Create - Add new role
     * @param roleBody as an object role
     * @return the role object saved
     */
    @PostMapping("/role")
    public RoleDTO createRole(@Valid @RequestBody RoleCreationDTO roleBody) {
        //Verify if the name is not already used in database
        Role existingRole = roleService.getRoleByRoleName(roleBody.getName())
                .orElse(null);
        if (existingRole != null) {
            return new RoleDTO(existingRole);
        } else {
            Role newRole = new Role();
            newRole.setName(roleBody.getName());
            Role savedRole = roleService.saveRole(newRole);
            return new RoleDTO(savedRole);
        }
    }

    /**
     * Read - Get one role by id
     * @param id The id of the role
     * @return role
     * @throws IllegalArgumentException if role is not found
     */
    @GetMapping("/role/{id}")
    public RoleDTO getRoleById(@PathVariable final long id) {
        Optional<Role> optionalRole = roleService.getRoleById(id);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            return new RoleDTO(role);
        } else {
            throw new IllegalArgumentException("Role introuvable");
        }
    }

    /**
     * Read - Get all roles
     * @return List of roles
     */
    @GetMapping("/role")
    public List<RoleDTO> getAllRoles() {
        Iterable<Role> roles = roleService.getAllRoles();
        List<RoleDTO> roleDTOs = new ArrayList<>();
        for (Role role : roles) {
            roleDTOs.add(new RoleDTO(role));
        }
        return roleDTOs;
    }

    /**
     * Patch - Update an existing role
     * @param id - The id of the role to update
     * @param roleBody - The role object to update
     * @return roleDTO - The role object updated
     * @throws IllegalArgumentException if role is not found
     * @throws IllegalStateException if name is already used
     */
    @PatchMapping("/role/{id}")
    public RoleDTO updateRole(@PathVariable final long id, @Valid @RequestBody RoleCreationDTO roleBody) {
        Optional<Role> roleInDatabase = roleService.getRoleById(id);
        if (roleInDatabase.isEmpty()) {
            throw new IllegalArgumentException("Role introuvable");
        }

        Role roleToUpdate = roleInDatabase.get();

        if (roleToUpdate.getName() != null && !roleBody.getName().isEmpty()) {
            roleToUpdate.setName(roleBody.getName());
        }

        Role savedRole = roleService.saveRole(roleToUpdate);
        return new RoleDTO(savedRole);
    }

    /**
     * Delete - Delete a role
     * @param id The id of the role to delete
     */
    @DeleteMapping("/role/{id}")
    public void deleteRole(@PathVariable final long id) {
        roleService.deleteRoleById(id);
    }
}
