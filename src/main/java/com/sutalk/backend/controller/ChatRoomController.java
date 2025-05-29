package com.sutalk.backend.controller;

import com.sutalk.backend.dto.ChatRoomRequestDTO;
import com.sutalk.backend.dto.ChatRoomResponseDTO;
import com.sutalk.backend.entity.ChatRoom;
import com.sutalk.backend.entity.Item;
import com.sutalk.backend.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<ChatRoomResponseDTO> getChatRoomById(@PathVariable Long chatRoomId) {
        ChatRoom room = chatRoomService.getChatRoomById(chatRoomId);
        Item item = room.getItemTransaction().getItem();

        List<String> itemImages = item.getItemImages().stream()
                .map(image -> image.getPhotoPath())
                .collect(Collectors.toList());

        // 최신 메시지 조회
        // (필요하다면 ChatRoomService에서 DTO로 변환하는 메서드를 따로 만들어도 됨)
        return ResponseEntity.ok(
                chatRoomService.getChatRoomsByUser(room.getBuyer().getUserid()).stream()
                        .filter(dto -> dto.getChatroomId().equals(chatRoomId))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("채팅방 DTO를 찾을 수 없습니다."))
        );
    }

    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<Void> deleteChatRoom(@PathVariable Long chatRoomId) {
        chatRoomService.deleteChatRoom(chatRoomId);
        return ResponseEntity.noContent().build();
    }
}
