package com.sutalk.backend.service;

import com.sutalk.backend.dto.ChatRoomResponseDTO;
import com.sutalk.backend.entity.ChatRoom;
import com.sutalk.backend.entity.ItemTransaction;
import com.sutalk.backend.entity.User;
import com.sutalk.backend.repository.ChatRoomRepository;
import com.sutalk.backend.repository.ItemTransactionRepository;
import com.sutalk.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

        return chatRoomRepository
                .findByBuyer_UseridAndSeller_UseridAndItemTransaction_Transactionid(buyerId, sellerId, itemTransactionId)
                .orElseGet(() -> chatRoomRepository.save(ChatRoom.builder()
                        .itemTransaction(transaction)
                        .buyer(buyer)
                        .seller(seller)
                        .createdAt(System.currentTimeMillis())
                        .build()));
    }

    public ChatRoom getChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다"));
    }

    public List<ChatRoomResponseDTO> getChatRoomsByUser(String userId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findByBuyer_UseridOrSeller_Userid(userId, userId);

        return chatRooms.stream().map(chatRoom -> new ChatRoomResponseDTO(
                chatRoom.getChatroomid(),
                chatRoom.getItemTransaction().getItem().getTitle(),
                chatRoom.getBuyer().getName(),
                chatRoom.getSeller().getName(),
                chatRoom.getCreatedAt()
        )).toList();
    }
}
