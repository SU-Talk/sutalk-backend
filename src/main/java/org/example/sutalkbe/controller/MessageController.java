package org.example.sutalkbe.controller;

import lombok.RequiredArgsConstructor;
import org.example.sutalkbe.dto.MessageDTO;
import org.example.sutalkbe.entity.Message; // ✅ 올바른 임포트
import org.example.sutalkbe.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody MessageDTO messageDTO) {
        Message message = messageService.sendMessage(
                messageDTO.getChatRoomId(),
                messageDTO.getSenderId(),
                messageDTO.getComment()
        );
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/{chatRoomId}")
    public List<Message> getMessages(@PathVariable Long chatRoomId) {
        return messageService.getMessagesByChatRoom(chatRoomId);
    }
}
