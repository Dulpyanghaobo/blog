package com.hab.blog.service;

import com.hab.blog.dto.PostDto;
import com.hab.blog.model.Post;
import com.hab.blog.model.User;
import com.hab.blog.repository.PostRepository;
import com.hab.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;
    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(PostDto postDto) {
        User user =userRepository.findById(postDto.getUserId());
        Post post1 = new Post();
        post1.setCategories(postDto.getCategories());
        post1.setExcerpt(postDto.getExcerpt());
        post1.setUser(user);
        post1.setTitle(postDto.getTitle());
        post1.setSlug(postDto.getSlug());
        post1.setReleaseSnapshot(postDto.getReleaseSnapshot());
        // Set other fields
        return postRepository.save(post1);
    }
}
