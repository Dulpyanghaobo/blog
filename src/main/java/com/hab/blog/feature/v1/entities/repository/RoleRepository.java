package com.hab.blog.feature.v1.entities.repository;

import com.hab.blog.feature.v1.entities.User.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
