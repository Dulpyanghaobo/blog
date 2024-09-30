package com.hab.blog.feature.v1.entities.repository;

import com.hab.blog.feature.v1.Publish.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository  extends JpaRepository<Post, Long> {
}
