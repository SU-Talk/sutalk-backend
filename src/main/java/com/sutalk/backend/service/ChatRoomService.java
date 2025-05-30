package com.sutalk.backend.service;

import com.sutalk.backend.dto.ChatRoomResponseDTO;
import com.sutalk.backend.entity.ChatRoom;
import com.sutalk.backend.entity.Item;
import com.sutalk.backend.entity.ItemTransaction;
import com.sutalk.backend.entity.User;
import com.sutalk.backend.repository.ChatMessageRepository;
import com.sutalk.backend.repository.ChatRoomRepository;
import com.sutalk.backend.repository.ItemTransactionRepository;
import com.sutalk.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ItemTransactionRepository itemTransactionRepository;
    private final UserRepository userRepository;

    public ChatRoomResponseDTO createChatRoom(Long transactionId, String buyerId, String sellerId) {
        System.out.println("📥 [채팅방 생성 요청] transactionId=" + transactionId + ", buyerId=" + buyerId + ", sellerId=" + sellerId);

        // 거래 정보 조회
        ItemTransaction transaction = itemTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("거래를 찾을 수 없습니다."));
        Item item = transaction.getItem();

        // ✅ 거래 ID 기준 중복 방 방지
        ChatRoom room = chatRoomRepository
                .findByItemTransaction_Transactionid(transactionId)
                .orElseGet(() -> {
                    System.out.println("🆕 [신규 채팅방 생성]");
                    User buyer = userRepository.findById(buyerId)
                            .orElseThrow(() -> new RuntimeException("구매자 정보를 찾을 수 없습니다."));
                    User seller = userRepository.findById(sellerId)
                            .orElseThrow(() -> new RuntimeException("판매자 정보를 찾을 수 없습니다."));

                    ChatRoom newRoom = ChatRoom.builder()
                            .itemTransaction(transaction)
                            .buyer(buyer)
                            .seller(seller)
                            .createdAt(System.currentTimeMillis())
                            .build();

                    ChatRoom saved = chatRoomRepository.save(newRoom);
                    System.out.println("✅ [채팅방 저장 성공] ID: " + saved.getChatroomid());
                    return saved;
                });

        // 응답 DTO 구성
        return new ChatRoomResponseDTO(
                room.getChatroomid(),
                item.getItemid(),
                item.getTitle(),
                room.getBuyer().getUserid(),        // ✅ buyerId 추가됨
                room.getBuyer().getName(),
                room.getSeller().getName(),
                room.getSeller().getUserid(),
                room.getCreatedAt()
        );
    }

    public List<ChatRoomResponseDTO> getChatRoomsByUser(String userId) {
        List<ChatRoom> rooms = chatRoomRepository.findByBuyer_UseridOrSeller_Userid(userId, userId);

        return rooms.stream()
                .map(room -> new ChatRoomResponseDTO(
                        room.getChatroomid(),
                        room.getItemTransaction().getItem().getItemid(),
                        room.getItemTransaction().getItem().getTitle(),
                        room.getBuyer().getUserid(),        // ✅ buyerId 추가됨
                        room.getBuyer().getName(),
                        room.getSeller().getName(),
                        room.getSeller().getUserid(),
                        room.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    public ChatRoom getChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));
    }

    @Transactional
    public void deleteChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));

        // 1. 채팅 메시지 먼저 삭제
        chatMessageRepository.deleteAllByChatRoom_Chatroomid(chatRoomId);

        // 2. ChatRoom 엔티티에서 itemTransaction 연관 해제 (nullable 해야 함)
        chatRoom.setItemTransaction(null); // 💡 여기가 포인트

        // 3. ChatRoom 삭제
        chatRoomRepository.delete(chatRoom);
    }

}
