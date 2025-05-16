package com.sutalk.backend.controller;

import com.sutalk.backend.dto.ChatRoomRequestDTO;
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

    @PostMapping
    public ResponseEntity<ChatRoomResponseDTO> createChatRoom(@RequestBody ChatRoomRequestDTO request) {
        ChatRoomResponseDTO response = chatRoomService.createChatRoom(
                request.getItemTransactionId(),
                request.getBuyerId(),
                request.getSellerId()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomResponseDTO>> getChatRoomsByUser(@RequestParam String userId) {
        List<ChatRoomResponseDTO> result = chatRoomService.getChatRoomsByUser(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{chatRoomId}") // ✅ 요거 추가!
    public ResponseEntity<ChatRoomResponseDTO> getChatRoomById(@PathVariable Long chatRoomId) {
        ChatRoom room = chatRoomService.getChatRoomById(chatRoomId);
        return ResponseEntity.ok(new ChatRoomResponseDTO(
                room.getChatroomid(),
                room.getItemTransaction().getItem().getItemid(),
                room.getItemTransaction().getItem().getTitle(),
                room.getBuyer().getUserid(),
                room.getBuyer().getName(),
                room.getSeller().getName(),
                room.getSeller().getUserid(),
                room.getCreatedAt()
        ));
    }

    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<Void> deleteChatRoom(@PathVariable Long chatRoomId) {
        chatRoomService.deleteChatRoom(chatRoomId);
        return ResponseEntity.noContent().build();
    }
}
