package org.example.sutalkbe.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.sutalkbe.entity.Image;
import org.example.sutalkbe.entity.User;
import org.example.sutalkbe.entity.Post;
import org.example.sutalkbe.repository.ImageRepository;
import org.example.sutalkbe.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final ImageRepository imageRepository;

    @Transactional
    public Post createPostWithImages(Post post, List<MultipartFile> images, Long userId) {
        User user = userService.getUserById(userId);
        post.setUser(user);
        Post savedPost = postRepository.save(post);

        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        Path uploadPath = Paths.get(uploadDir);

        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("디렉토리 생성 실패: " + e.getMessage());
        }

        if (images != null && !images.isEmpty()) {
            for (MultipartFile file : images) {
                String originalFileName = file.getOriginalFilename();
                String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
                String saveFileName = UUID.randomUUID() + ext;
                Path filePath = uploadPath.resolve(saveFileName);

                try {
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    Image image = new Image();
                    image.setUrl("/uploads/" + saveFileName);
                    image.setPost(savedPost);
                    imageRepository.save(image);
                } catch (IOException e) {
                    throw new RuntimeException("이미지 저장 실패: " + e.getMessage());
                }
            }
        }
        return savedPost;
    }

    public Post getPostById(Long postId) {
        return postRepository.findByIdWithImages(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public List<Post> getPosts(int page) {
        return postRepository.findAll();
    }
}
