package com.hab.blog.service;

import com.hab.blog.model.Role;
import com.hab.blog.model.RoleName;
import com.hab.blog.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String roleName) {
        return roleRepository.findByName(RoleName.valueOf(roleName))
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));
    }
}
