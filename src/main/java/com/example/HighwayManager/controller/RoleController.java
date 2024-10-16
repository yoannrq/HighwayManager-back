package com.example.HighwayManager.controller;

import com.example.HighwayManager.model.Role;
import com.example.HighwayManager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @param role as an object role
     * @return the role object saved
     */
    @PostMapping("/role")
    public Role createRole(@RequestBody Role role) {
        //Verify if the name is not already used in database
        Optional<Role> existingRole = roleService.getRoleByRoleName(role.getName());
        if (existingRole.isPresent()) {
            return existingRole.get();
        } else {
            return roleService.saveRole(role);
        }
    }

    /**
     * Read - Get one role by id
     * @param id The id of the role
     * @return role || null
     */
    @GetMapping("/role/{id}")
    public Role getRoleById(@PathVariable final long id) {
        Optional<Role> role = roleService.getRoleById(id);
        return role.orElse(null);
    }

    /**
     * Read - Get all roles
     * @return An iterable object of roles
     */
    @GetMapping("/role")
    public Iterable<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    /**
     * Patch - Update an existing role
     * @param id - The id of the role to update
     * @param roleBody - The role object to update
     * @return role || null - The role object updated
     * @throws IllegalStateException if name is already used
     */
    @PatchMapping("/role/{id}")
    public Role updateRole(@PathVariable final long id, @RequestBody Role roleBody) {
        Optional<Role> roleInDatabase = roleService.getRoleById(id);
        if (roleInDatabase.isPresent()) {
            Role roleToUpdate = roleInDatabase.get();

            String name = roleBody.getName();
            if (name != null && !name.isEmpty()) {
                //Verify if role name is not already used in database
                Optional<Role> isRoleNameAlreadyUsed = roleService.getRoleByRoleName(name);
                if (isRoleNameAlreadyUsed.isEmpty()) {
                    roleToUpdate.setName(name);
                } else {
                    throw new IllegalStateException("Ce nom de rôle est déjà utilisé");
                }

            }

            return roleService.saveRole(roleToUpdate);
        } else {
            return null;
        }
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
