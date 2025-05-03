// 📁 service/ChatMessageService.java
package com.sutalk.backend.service;

import com.sutalk.backend.entity.ChatMessage;
import com.sutalk.backend.entity.ChatRoom;
import com.sutalk.backend.entity.User;
import com.sutalk.backend.repository.ChatMessageRepository;
import com.sutalk.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;
    private final UserRepository userRepository;

    public ChatMessage sendMessage(Long chatRoomId, String senderId, String content) {
        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatRoomId);
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("보낸 사람을 찾을 수 없습니다"));

        ChatMessage message = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .content(content)
                .sentAt(System.currentTimeMillis())
                .isRead(false)
                .build();

        return chatMessageRepository.save(message);
    }

    public List<ChatMessage> getMessagesByChatRoom(Long chatRoomId) {
        return chatMessageRepository.findByChatRoom_ChatroomidOrderBySentAtAsc(chatRoomId);
    }
}
