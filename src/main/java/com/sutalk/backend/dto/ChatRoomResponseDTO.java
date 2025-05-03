package com.sutalk.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoomResponseDTO {
    private Long chatroomId;         // ✅ camelCase로 수정
    private String itemTitle;
    private String buyerUsername;
    private String sellerUsername;
    private Long createdAt;
}
