package org.example.sutalkbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoomResponse {
    private Long id;
    private String postTitle;
    private String buyerName;
    private String sellerName;
}