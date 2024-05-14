package com.hab.blog.repository;

import com.hab.blog.api.v1.auth.Entity.User;
import com.hab.blog.api.v1.auth.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

//    @TestModel
//    public void findByEmailShouldReturnUser() {
//        // Arrange - given
//        User user = new User();
//        user.setDisplayName("TestModel User");
//        user.setAvatar("TestModel Avatar");
//        user.setEmail("test@example.com");
//        user.setPassword("password");
//        entityManager.persist(user);
//        entityManager.flush();
//
//        // Act - when
//        Optional<User> found = userRepository.findByEmail(user.getEmail());
//
//        // Assert - then
//        assertThat(found).isPresent();
//        assertThat(found.orElseThrow().getEmail()).isEqualTo(user.getEmail());
//    }

    @Test
    public void findByEmailShouldReturnEmptyIfUserNotFound() {
        // Arrange - given
        String email = "nonexisting@example.com";

        // Act - when
        Optional<User> found = userRepository.findByEmail(email);

        // Assert - then
        assertThat(found).isNotPresent();
    }
}
