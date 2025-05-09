package com.sutalk.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoomResponseDTO {
    private Long chatroomId;
    private Long itemId;
    private String itemTitle;
    private String buyerUsername;
    private String sellerUsername;
    private String sellerId; // ✅ 추가
    private Long createdAt;
}
