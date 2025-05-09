package com.sutalk.backend.controller;

import com.sutalk.backend.dto.ReviewRequestDTO;
import com.sutalk.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewRequestDTO dto) {
        reviewService.createReview(dto);
        return ResponseEntity.ok("리뷰가 저장되었습니다.");
    }
}
