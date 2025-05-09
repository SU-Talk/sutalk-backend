package com.sutalk.backend.service;

import com.sutalk.backend.dto.ReviewRequestDTO;
import com.sutalk.backend.entity.Item;
import com.sutalk.backend.entity.Review;
import com.sutalk.backend.entity.User;
import com.sutalk.backend.repository.ItemRepository;
import com.sutalk.backend.repository.ReviewRepository;
import com.sutalk.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public void createReview(ReviewRequestDTO dto) {
        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
        User buyer = userRepository.findById(dto.getBuyerId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 중복 리뷰 방지
        if (reviewRepository.existsByItemAndBuyer(item, buyer)) {
            throw new IllegalStateException("이미 리뷰를 작성했습니다.");
        }

        Review review = new Review();
        review.setItem(item);
        review.setBuyer(buyer);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        reviewRepository.save(review);
    }
}
