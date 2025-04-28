package org.example.sutalkbe.controller;

import lombok.RequiredArgsConstructor;
import org.example.sutalkbe.entity.ChatRoom;
import org.example.sutalkbe.service.ChatRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat-rooms")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping
    public ResponseEntity<ChatRoom> createChatRoom(
            @RequestParam Long postId,
            @RequestParam Long buyerId,
            @RequestParam Long sellerId
    ) {
        ChatRoom chatRoom = chatRoomService.createChatRoom(postId, buyerId, sellerId);
        return ResponseEntity.ok(chatRoom);
    }
}
