package com.hab.blog.feature.v1.Publish;

import com.hab.blog.feature.v1.entities.repository.UserRepository;
import com.hab.blog.feature.v1.entities.User.User;
import com.hab.blog.feature.v1.entities.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
