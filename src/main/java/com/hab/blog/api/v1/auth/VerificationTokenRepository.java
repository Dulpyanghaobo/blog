package com.hab.blog.api.v1.auth;

import com.hab.blog.api.v1.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    Optional<VerificationToken> findByUserId(Long userId);

    Optional<VerificationToken> findByUserIdAndToken(Long userId, String token);

}
