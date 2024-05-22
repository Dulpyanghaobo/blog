package com.hab.blog.api.v1.auth;

import com.hab.blog.api.v1.auth.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.userName = :username")
    Optional<User> findByUserNameWithRoles(@Param("username") String username);

    User findById(long id);

    Optional<User> findUsersByUserName(String username);
    Optional<User> findUserByEmail(String email);

    boolean existsByUserName(String displayName);

    boolean existsByEmail(String email);

    // 这里可以定义查询方法，Spring Data JPA会自动实现
}
