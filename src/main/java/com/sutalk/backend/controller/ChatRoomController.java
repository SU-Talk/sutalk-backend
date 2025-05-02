package com.sutalk.backend.controller;

import com.sutalk.backend.dto.ChatRoomResponseDTO;
import com.sutalk.backend.entity.ChatRoom;
import com.sutalk.backend.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat-rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 원래 존재하던 ChatRoom 리턴용 엔드포인트
    @PostMapping
    public ResponseEntity<ChatRoom> createChatRoom(
            @RequestParam Long itemTransactionId,
            @RequestParam String buyerId,
            @RequestParam String sellerId
    ) {
        ChatRoom chatRoom = chatRoomService.createChatRoom(itemTransactionId, buyerId, sellerId);
        return ResponseEntity.ok(chatRoom);
    }

    // 새로 추가된: DTO 리턴용 엔드포인트
    @PostMapping("/dto")
    public ResponseEntity<ChatRoomResponseDTO> createChatRoomDto(
            @RequestParam Long itemTransactionId,
            @RequestParam String buyerId,
            @RequestParam String sellerId
    ) {
        ChatRoom chatRoom = chatRoomService.createChatRoom(itemTransactionId, buyerId, sellerId);

        ChatRoomResponseDTO dto = new ChatRoomResponseDTO(
                chatRoom.getChatroomid(),
                chatRoom.getItemTransaction().getItem().getTitle(), // item title 접근
                chatRoom.getBuyer().getName(), // User의 name 필드
                chatRoom.getSeller().getName(),
                chatRoom.getCreatedAt()
        );

        return ResponseEntity.ok(dto);
    }
}
