package com.hab.blog.api.v1.controller;


import com.hab.blog.api.v1.model.Post;
import com.hab.blog.api.v1.service.PostService;
import com.hab.blog.api.v1.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/posts") // 控制器的基础URL
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController (PostService postService) {
        this.postService = postService;
    }
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostDto post) {
        try {
            // 假设用户的信息是通过安全上下文或者请求参数传递进来的
            // 在这里，我们直接将接收到的Post对象保存
            Post createdPost = postService.createPost(post);
            // 创建成功后，返回新创建的帖子和HTTP状态201 Created
            return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
        } catch (Exception e) {
            // 发生异常时返回HTTP状态500 Internal Server Error
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createPost(@ModelAttribute PostDto postDto,
                                        @RequestParam("file") MultipartFile file) {
        // Process the postDto and file here
        // Save file to the server or a service like AWS S3 or Alibaba Cloud OSS
        // ...

        return ResponseEntity.ok().body("Post and file uploaded successfully!");
    }
}
