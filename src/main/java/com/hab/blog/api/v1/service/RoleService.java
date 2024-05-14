package com.hab.blog.api.v1.service;

import com.hab.blog.api.v1.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

//    public Role findByName(String roleName) {
//        return roleRepository.findBy(RoleName.valueOf(roleName))
//                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));
//    }
}
