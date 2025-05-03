package com.sutalk.backend.controller;

import com.sutalk.backend.dto.ChatRoomResponseDTO;
import com.sutalk.backend.entity.ChatRoom;
import com.sutalk.backend.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat-rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 채팅방 생성 (Entity 반환)
    @PostMapping
    public ResponseEntity<ChatRoom> createChatRoom(
            @RequestParam Long itemTransactionId,
            @RequestParam String buyerId,
            @RequestParam String sellerId
    ) {
        ChatRoom chatRoom = chatRoomService.createChatRoom(itemTransactionId, buyerId, sellerId);
        return ResponseEntity.ok(chatRoom);
    }

    // 채팅방 생성 (DTO 반환)
    @PostMapping("/dto")
    public ResponseEntity<ChatRoomResponseDTO> createChatRoomDto(
            @RequestParam Long itemTransactionId,
            @RequestParam String buyerId,
            @RequestParam String sellerId
    ) {
        ChatRoom chatRoom = chatRoomService.createChatRoom(itemTransactionId, buyerId, sellerId);
        ChatRoomResponseDTO dto = new ChatRoomResponseDTO(
                chatRoom.getChatroomid(),
                chatRoom.getItemTransaction().getItem().getTitle(),
                chatRoom.getBuyer().getName(),
                chatRoom.getSeller().getName(),
                chatRoom.getCreatedAt()
        );
        return ResponseEntity.ok(dto);
    }

    // 특정 유저의 채팅방 목록 조회
    @GetMapping
    public ResponseEntity<List<ChatRoomResponseDTO>> getChatRoomsByUser(@RequestParam String userId) {
        List<ChatRoomResponseDTO> result = chatRoomService.getChatRoomsByUser(userId);
        return ResponseEntity.ok(result);
    }
}
