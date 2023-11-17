package com.hab.blog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Data // Lombok annotation to create getters, setters, equals, hashCode and toString methods
@NoArgsConstructor // Lombok annotation to create a no-args constructor
@Entity // JPA annotation to signify a database entity
@Table(name = "users") // JPA annotation to specify the table name if it's different from the class name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<OKR> okrs;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Task> tasks;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "avatar")
    private String avatar;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phone;

    @Size(min = 8)
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;

}
