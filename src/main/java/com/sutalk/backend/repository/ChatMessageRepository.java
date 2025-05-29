package com.sutalk.backend.repository;

import com.sutalk.backend.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // 채팅방의 모든 메시지 sentAt 오름차순(=채팅방 메시지 내역 조회)
    List<ChatMessage> findByChatRoom_ChatroomidOrderBySentAtAsc(Long chatRoomId);

    // 채팅방별 최신 메시지 1개 sentAt 내림차순 (최신 메시지)
    ChatMessage findTopByChatRoom_ChatroomidOrderBySentAtDesc(Long chatRoomId);

    // 해당 채팅방의 모든 메시지 삭제
    void deleteAllByChatRoom_Chatroomid(Long chatRoomId);
}
