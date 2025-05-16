package com.sutalk.backend.controller;

import com.sutalk.backend.dto.MessageResponseDTO;
import com.sutalk.backend.entity.ChatMessage;
import com.sutalk.backend.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat-messages")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<List<MessageResponseDTO>> getMessagesByChatRoom(@PathVariable Long chatRoomId) {
        List<ChatMessage> messages = chatMessageService.getMessagesByChatRoom(chatRoomId);
        List<MessageResponseDTO> dtos = messages.stream().map(msg ->
                MessageResponseDTO.builder()
                        .messageId(msg.getMessageid())
                        .chatRoomId(msg.getChatRoom().getChatroomid())
                        .senderId(msg.getSender().getUserid())
                        .content(msg.getContent())
                        .sentAt(msg.getSentAt())
                        .build()
        ).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}
