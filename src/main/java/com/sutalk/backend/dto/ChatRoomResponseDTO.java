package com.sutalk.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoomResponseDTO {
    private Long chatroomid;
    private String itemTitle;
    private String buyerUsername;
    private String sellerUsername;
    private Long createdAt;
}
