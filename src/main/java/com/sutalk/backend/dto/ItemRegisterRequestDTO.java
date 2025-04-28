package com.sutalk.backend.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ItemRegisterRequestDTO {

    private String title;
    private String description;
    private Integer price;
    private String category;
    private String sellerId;
    private List<String> itemImages;  // 이미지 URL 리스트

}
