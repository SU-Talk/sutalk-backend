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
    private List<String> itemImages;

    // 최신 메시지 관련 필드
    private String lastMessage;
    private Long lastMessageTime; // epoch millis
}
