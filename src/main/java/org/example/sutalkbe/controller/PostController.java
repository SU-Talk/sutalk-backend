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

    @GetMapping
    public List<PostResponseDTO> getPosts(@RequestParam(defaultValue = "1") int page) {
        return postService.getPosts(page).stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{postId}")
    public PostResponseDTO getPostById(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        return new PostResponseDTO(post);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Post createPost(
            @RequestPart("post") Post post,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        return postService.createPostWithImages(post, images, 1L);
    }
}