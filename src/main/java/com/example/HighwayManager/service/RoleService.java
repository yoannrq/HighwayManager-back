package com.example.HighwayManager.service;

import com.example.HighwayManager.model.Role;
import com.example.HighwayManager.repository.RoleRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> getRoleById(final Long id) {
        return roleRepository.findById(id);
    }

    public Optional<Role> getRoleByRoleName(final String roleName) {
        return roleRepository.findByName(roleName);
    }

    public Iterable<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void deleteRoleById(final Long id) {
        roleRepository.deleteById(id);
    }

    public Role saveRole(final Role role) {
        return roleRepository.save(role);
    }
}
