package com.sutalk.backend.dto;

import lombok.Data;

@Data
public class MessageDTO {
    private Long chatRoomId;
    private String senderId;
    private String comment;
}
