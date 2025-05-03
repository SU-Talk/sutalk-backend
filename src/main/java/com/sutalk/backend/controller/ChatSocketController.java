// 📁 controller/ChatSocketController.java
package com.sutalk.backend.controller;

import com.sutalk.backend.dto.MessageDTO;
import com.sutalk.backend.dto.MessageResponseDTO;
import com.sutalk.backend.entity.ChatMessage;
import com.sutalk.backend.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload MessageDTO messageDTO) {
        ChatMessage saved = chatMessageService.sendMessage(
                messageDTO.getChatRoomId(),
                messageDTO.getSenderId(),
                messageDTO.getContent()  // ✅ message → content
        );

        MessageResponseDTO responseDTO = MessageResponseDTO.builder()
                .messageId(saved.getMessageid())
                .chatRoomId(saved.getChatRoom().getChatroomid())
                .senderId(saved.getSender().getUserid())
                .content(saved.getContent())
                .sentAt(saved.getSentAt())
                .build();

        messagingTemplate.convertAndSend("/topic/chat/" + responseDTO.getChatRoomId(), responseDTO);
    }
}
