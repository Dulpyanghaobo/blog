package com.hab.blog.repository;

import com.hab.blog.model.OKR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OkrRepository extends JpaRepository<OKR, Long> {
}
