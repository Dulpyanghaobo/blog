package com.hab.blog.repository;

import com.hab.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findById(long id);

    boolean existsByDisplayName(String displayName);

    boolean existsByEmail(String email);

    // 这里可以定义查询方法，Spring Data JPA会自动实现
}
