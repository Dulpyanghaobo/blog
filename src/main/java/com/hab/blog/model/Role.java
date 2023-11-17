package com.hab.blog.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName name; // RoleName是一个枚举，列出了所有可能的角色

    public Role(RoleName roleName) {
        this.name = roleName;
    }

    public Role() {

    }
}

