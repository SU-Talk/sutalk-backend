package com.sutalk.backend.controller;

import com.sutalk.backend.dto.MessageDTO;
import com.sutalk.backend.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload MessageDTO messageDTO) {
        System.out.println("📨 받은 메시지 DTO: " + messageDTO);
        chatMessageService.sendMessage(messageDTO);
    }
}
