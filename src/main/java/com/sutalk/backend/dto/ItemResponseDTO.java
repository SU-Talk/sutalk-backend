package com.sutalk.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponseDTO {
    private Long itemid;
    private String title;
    private String category;
    private String description;
    private int price;
    private String regdate;
    private String meetLocation;              // ✅ 이름 통일
    private String sellerId;
    private List<String> itemImages;          // ✅ 이름 통일
}
