package com.hab.blog.feature.v1.init;

import com.hab.blog.feature.v1.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
//        // 检查并创建必要的角色
//        if (roleRepository.findByName(Role.ROLE_USER).isEmpty()) {
//            roleRepository.save(new Role(RoleName.ROLE_USER));
//        }
//
//        if (roleRepository.findByName(RoleName.ROLE_ADMIN).isEmpty()) {
//            roleRepository.save(new Role(RoleName.ROLE_ADMIN));
//        }

    }
}

