package org.example.sutalkbe;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.sutalkbe.entity.User;
import org.example.sutalkbe.entity.Post;
import org.example.sutalkbe.repository.PostRepository;
import org.example.sutalkbe.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
public class SuTalkBeApplication {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public static void main(String[] args) {
        SpringApplication.run(SuTalkBeApplication.class, args);
    }

    @PostConstruct
    public void started() {
        System.out.println("서버가 실행중입니다 🚀");
    }

    @Bean
    CommandLineRunner initData() {
        return args -> {
            // User 생성
            User user = new User();
            user.setUsername("user1");
            user.setPassword("password123");
            userRepository.save(user);

            // Post 생성
            Post post = new Post();
            post.setTitle("맥북 프로 팝니다");
            post.setComment("2023년형 최신 모델입니다.");
            post.setUser(user);
            postRepository.save(post);
        };


    }
}