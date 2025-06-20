package com.hab.blog.api.v1.repository;

import com.hab.blog.api.v1.Objective.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
