package com.sutalk.backend.service;

import com.sutalk.backend.dto.ReviewRequestDTO;
import com.sutalk.backend.dto.ReviewResponseDTO;
import com.sutalk.backend.dto.ReviewSummaryDTO;
import com.sutalk.backend.entity.Item;
import com.sutalk.backend.entity.ItemTransaction;
import com.sutalk.backend.entity.Review;
import com.sutalk.backend.entity.User;
import com.sutalk.backend.repository.ItemRepository;
import com.sutalk.backend.repository.ItemTransactionRepository;
import com.sutalk.backend.repository.ReviewRepository;
import com.sutalk.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemTransactionRepository itemTransactionRepository;

    public List<ReviewSummaryDTO> getReviewSummariesBySeller(String sellerId) {
        return reviewRepository.findReviewSummariesBySellerId(sellerId);
    }

    public List<ReviewResponseDTO> getReviewsBySellerDetailed(String sellerId) {
        List<Review> reviews = reviewRepository.findByReviewee_UseridOrderByCreatedAtDesc(sellerId);
        return reviews.stream()
                .map(ReviewResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }


    public void createReview(ReviewRequestDTO dto) {
        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
        User buyer = userRepository.findById(dto.getBuyerId())
                .orElseThrow(() -> new IllegalArgumentException("Buyer not found"));
        User reviewee = userRepository.findById(dto.getRevieweeId())
                .orElseThrow(() -> new IllegalArgumentException("Reviewee not found"));
        ItemTransaction transaction = itemTransactionRepository.findById(dto.getTransactionId())
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        if (reviewRepository.existsByItemAndBuyer(item, buyer)) {
            throw new IllegalStateException("이미 리뷰를 작성했습니다.");
        }

        Review review = new Review();
        review.setItem(item);
        review.setBuyer(buyer);
        review.setReviewee(reviewee);
        review.setReviewer(buyer);
        review.setTransaction(transaction);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        reviewRepository.save(review);
    }
}
