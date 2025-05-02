package org.example.sutalkbe.controller;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

import org.example.sutalkbe.dto.PostResponseDTO;
import org.example.sutalkbe.entity.Post;
import org.example.sutalkbe.service.PostService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 전체 게시글 조회: GET /api/posts?page=1
    @GetMapping
    public List<PostResponseDTO> getPosts(@RequestParam(defaultValue = "1") int page) {
        List<Post> posts = postService.getPosts(page);
        return posts.stream().map(PostResponseDTO::new).toList();
    }

    // 단일 게시글 상세조회: GET /api/posts/{postId}
    @GetMapping("/{postId}")
    public PostResponseDTO getPostById(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        return new PostResponseDTO(post);
    }

    // 게시글 등록: POST /api/posts
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Post createPost(
            @RequestPart("post") Post post,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        return postService.createPostWithImages(post, images, 1L);
    }
}
