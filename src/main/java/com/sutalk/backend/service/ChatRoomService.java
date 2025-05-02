package com.sutalk.backend.service;


import com.sutalk.backend.entity.ChatRoom;
import com.sutalk.backend.entity.ItemTransaction;
import com.sutalk.backend.entity.User;
import com.sutalk.backend.repository.ChatRoomRepository;
import com.sutalk.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sutalk.backend.repository.ItemTransactionRepository;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ItemTransactionRepository itemTransactionRepository;
    private final UserRepository userRepository;

    public ChatRoom createChatRoom(Long itemTransactionId, String buyerId, String sellerId) {
        ItemTransaction transaction = itemTransactionRepository.findById(itemTransactionId)
                .orElseThrow(() -> new RuntimeException("거래 내역을 찾을 수 없습니다"));

        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("구매자 정보를 찾을 수 없습니다"));
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("판매자 정보를 찾을 수 없습니다"));

        ChatRoom chatRoom = ChatRoom.builder()
                .itemTransaction(transaction)
                .buyer(buyer)
                .seller(seller)
                .createdAt(System.currentTimeMillis()) // 또는 LocalDateTime.now()
                .build();

        return chatRoomRepository.save(chatRoom);
    }

    public ChatRoom getChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다"));
    }
}
