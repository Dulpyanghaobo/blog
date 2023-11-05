package com.hab.blog.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data // Lombok annotation to create getters, setters, equals, hashCode and toString methods
@NoArgsConstructor // Lombok annotation to create a no-args constructor
@Entity // JPA annotation to signify a database entity
@Table(name = "users") // JPA annotation to specify the table name if it's different from the class name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "avatar")
    private String avatar;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(length = 500) // Specify the column length if you want to limit the bio length
    private String bio;

    @Column(name = "registered_at", nullable = false, updatable = false)
    private Instant registeredAt = Instant.now();

    @Column(name = "two_factor_auth_enabled", nullable = false)
    private Boolean twoFactorAuthEnabled = false; // Default it to false

    @Column(nullable = false)
    private Boolean disabled = false; // Default it to false

    // You can add more fields, constructors, and methods as needed.
}
