package com.sutalk.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDTO {
    private Long itemId;
    private String buyerId;
    private int rating;
    private String comment;
}
