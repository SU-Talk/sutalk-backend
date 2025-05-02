package com.sutalk.backend.controller;

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
    public void sendMessage(@Payload ChatMessage chatMessage) {
        // 저장
        ChatMessage saved = chatMessageService.sendMessage(
                chatMessage.getChatRoom().getChatroomid(),
                chatMessage.getSender().getUserid(),
                chatMessage.getContent()
        );

        // /topic/chat/{chatRoomId}로 브로드캐스트
        messagingTemplate.convertAndSend(
                "/topic/chat/" + saved.getChatRoom().getChatroomid(),
                saved
        );
    }
}
