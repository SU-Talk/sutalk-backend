package com.sutalk.backend.repository;

import com.sutalk.backend.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChatRoom_ChatroomidOrderBySentAtAsc(Long chatroomid);
}
