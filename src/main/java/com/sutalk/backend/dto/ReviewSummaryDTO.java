package com.sutalk.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewSummaryDTO {
    private String comment;
    private int rating;
    private LocalDateTime createdAt;
    private String reviewerName;
    private String itemTitle;
}
