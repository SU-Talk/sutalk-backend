package com.sutalk.backend.service;

import com.sutalk.backend.dto.MessageDTO;
import com.sutalk.backend.entity.ChatMessage;
import com.sutalk.backend.entity.ChatRoom;
import com.sutalk.backend.entity.User;
import com.sutalk.backend.repository.ChatMessageRepository;
import com.sutalk.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void sendMessage(MessageDTO dto) {
        ChatRoom chatRoom = chatRoomService.getChatRoomById(dto.getChatRoomId());
        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new RuntimeException("보낸 사람을 찾을 수 없습니다"));

        ChatMessage message = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .content(dto.getContent())
                .sentAt(System.currentTimeMillis())
                .isRead(false)
                .build();

        chatMessageRepository.save(message);

        messagingTemplate.convertAndSend("/topic/chat/" + dto.getChatRoomId(), dto);
    }

    public List<ChatMessage> getMessagesByChatRoom(Long chatRoomId) {
        return chatMessageRepository.findByChatRoom_ChatroomidOrderBySentAtAsc(chatRoomId);
    }
}
