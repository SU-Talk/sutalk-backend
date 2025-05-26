package com.sutalk.backend.controller;

import com.sutalk.backend.dto.UpdateUserIdRequest;
import com.sutalk.backend.dto.UserProfileResponseDTO;
import com.sutalk.backend.entity.Review;
import com.sutalk.backend.entity.User;
import com.sutalk.backend.repository.ItemRepository;
import com.sutalk.backend.repository.ItemTransactionRepository;
import com.sutalk.backend.repository.ReviewRepository;
import com.sutalk.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemTransactionRepository itemTransactionRepository;

    // ✅ 판매자 프로필 정보 + 평균 별점 + 후기 개수
    @GetMapping("/{userid}")
    public ResponseEntity<UserProfileResponseDTO> getUserProfile(@PathVariable String userid) {
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Double avg = reviewRepository.findAverageRatingByUser(user);
        int count = reviewRepository.countByReviewee(user);

        UserProfileResponseDTO dto = UserProfileResponseDTO.builder()
                .userid(user.getUserid())
                .name(user.getName())
                .email(user.getEmail())
                .averageRating(avg != null ? avg : 0.0)
                .reviewCount(count)
                .build();

        return ResponseEntity.ok(dto);
    }

    // ✅ 받은 후기 리스트 조회
    @GetMapping("/{userid}/reviews")
    public List<Review> getReviewsForUser(@PathVariable String userid) {
        return reviewRepository.findByReviewee_Userid(userid);
    }

    // ✅ 회원가입
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userRepository.existsById(user.getUserid())) {
            throw new RuntimeException("이미 존재하는 ID입니다.");
        }
        User saved = userRepository.save(user);
        return ResponseEntity.ok(saved);
    }

    // ✅ 로그인용 사용자 존재 여부 확인
    @GetMapping("/{userid}/check")
    public ResponseEntity<User> getUserById(@PathVariable String userid) {
        return userRepository.findById(userid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ ID 변경 – FK 안전하게 바꾼 후 기존 삭제
    @PatchMapping("/{userid}")
    @Transactional
    public ResponseEntity<?> updateUserId(
            @PathVariable String userid,
            @RequestBody UpdateUserIdRequest request
    ) {
        String newUserId = request.getNewUserId();

        if (!userRepository.existsById(userid)) {
            return ResponseEntity.notFound().build();
        }

        if (userRepository.existsById(newUserId)) {
            return ResponseEntity.status(409).body("이미 존재하는 ID입니다.");
        }

        // 1. 기존 유저 정보 조회
        User existing = userRepository.findById(userid).orElseThrow();

        // 2. 새 ID로 새로운 유저 생성 (먼저 INSERT 해야 FK가 참조 가능)
        User newUser = new User();
        newUser.setUserid(newUserId);
        newUser.setEmail(existing.getEmail());
        newUser.setName(existing.getName());
        newUser.setPassword(existing.getPassword());
        newUser.setPhone(existing.getPhone());
        newUser.setStatus(existing.getStatus());
        userRepository.save(newUser); // FK 업데이트 전에 존재하게 만듦

        userRepository.flush(); // DB에 실제로 반영되게 강제 flush

        // 3. FK 필드 먼저 전부 업데이트
        itemRepository.updateSellerUserId(userid, newUserId);
        itemRepository.updateBuyerUserId(userid, newUserId);
        itemTransactionRepository.updateUserId(userid, newUserId);
        // 🔁 필요 시 review, chat 등 FK도 여기에 추가 가능

        // 4. 기존 유저 삭제
        userRepository.deleteById(userid);

        return ResponseEntity.ok().build();
    }
}
