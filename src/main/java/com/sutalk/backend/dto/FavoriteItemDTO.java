package com.sutalk.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteItemDTO {
    private Long itemid;
    private String title;
    private String thumbnail;
    private Integer price;
    private Long likeCount;
}
