package com.sutalk.backend.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MessageResponseDTO {
    private Long messageId;
    private Long chatRoomId;
    private String senderId;
    private String content;
    private Long sentAt;
}
