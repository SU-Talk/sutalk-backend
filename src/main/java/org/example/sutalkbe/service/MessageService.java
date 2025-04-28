package org.example.sutalkbe.service;

import lombok.RequiredArgsConstructor;

import org.example.sutalkbe.entity.ChatRoom;
import org.example.sutalkbe.entity.Message; // ✅ 올바른 임포트
import org.example.sutalkbe.entity.User;
import org.example.sutalkbe.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRoomService chatRoomService;
    private final UserService userService;

    public Message sendMessage(Long chatRoomId, Long senderId, String content) {
        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatRoomId);
        User sender = userService.getUserById(senderId);

        Message message = new Message();
        message.setChatRoom(chatRoom);
        message.setSender(sender);
        message.setMessage(content);
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByChatRoom(Long chatRoomId) { // ✅ 반환 타입 수정
        return messageRepository.findByChatRoomIdOrderBySentAtAsc(chatRoomId);
    }
}
