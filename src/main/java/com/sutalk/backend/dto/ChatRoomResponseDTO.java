package com.sutalk.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatRoomResponseDTO {
    private Long chatroomId;
    private Long itemId;
    private String itemTitle;
    private String buyerId;
    private String buyerUsername;
    private String sellerUsername;
    private String sellerId;
    private Long createdAt;

    private String meetLocation;
    private List<String> itemImages; // ✅ itemImages 직접 추가!
}
