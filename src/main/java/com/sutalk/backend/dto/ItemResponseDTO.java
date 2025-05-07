package com.sutalk.backend.dto;

import com.sutalk.backend.entity.Item;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

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
    private String meetLocation;
    private String sellerId;
    private List<String> itemImages;
    private String status;

    public static ItemResponseDTO from(Item item) {
        return ItemResponseDTO.builder()
                .itemid(item.getItemid())
                .title(item.getTitle())
                .category(item.getCategory())
                .description(item.getDescription())
                .price(item.getPrice())
                .regdate(item.getRegdate() + "")
                .meetLocation(item.getMeetLocation())
                .sellerId(item.getSeller().getUserid())
                .itemImages(item.getItemImages().stream()
                        .map(image -> image.getPhotoPath())
                        .collect(Collectors.toList()))
                .build();
    }
}
