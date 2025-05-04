package com.sutalk.backend.controller;

import com.sutalk.backend.dto.ChatRoomRequestDTO;
import com.sutalk.backend.dto.ChatRoomResponseDTO;
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
        System.out.println("📨 [채팅방 생성 API 호출] request = " + request);

        ChatRoomResponseDTO response = chatRoomService.createChatRoom(
                request.getItemTransactionId(),
                request.getBuyerId(),
                request.getSellerId()
        );

        System.out.println("✅ [채팅방 응답 DTO] = " + response.getChatroomId());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomResponseDTO>> getChatRoomsByUser(@RequestParam String userId) {
        List<ChatRoomResponseDTO> result = chatRoomService.getChatRoomsByUser(userId);
        return ResponseEntity.ok(result);
    }
}
