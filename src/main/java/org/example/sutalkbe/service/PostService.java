package org.example.sutalkbe.service;

import org.springframework.transaction.annotation.Transactional;
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

        String thumbnailUrl = null;
        if (images != null && !images.isEmpty()) {
            for (int i = 0; i < images.size(); i++) {
                MultipartFile file = images.get(i);
                String originalFileName = file.getOriginalFilename();
                String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
                String saveFileName = UUID.randomUUID().toString().replace("-", "") + ext;
                Path filePath = uploadPath.resolve(saveFileName);

                try {
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    String imageUrl = "/uploads/" + saveFileName;

                    // 첫 번째 이미지를 썸네일로 지정
                    if (i == 0) {
                        thumbnailUrl = imageUrl;
                    }

                    Image image = new Image();
                    image.setUrl(imageUrl);
                    image.setPost(savedPost);
                    imageRepository.save(image);
                } catch (IOException e) {
                    throw new RuntimeException("이미지 저장 실패: " + e.getMessage());
                }
            }
        }

        // 썸네일 필드에 첫 번째 이미지의 URL 저장
        if (thumbnailUrl != null) {
            savedPost.setThumbnail(thumbnailUrl);
            postRepository.save(savedPost); // 썸네일 정보 반영
        }

        return savedPost;
    }

        @Transactional(readOnly = true)
        public Post getPostById(Long postId) {
            return postRepository.findByIdWithImages(postId)
                    .orElseThrow(() -> new RuntimeException("Post not found"));
        }


    public List<Post> getPosts(int page) {
        return postRepository.findAll();
    }
}
