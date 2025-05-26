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

    /**
     * 채팅방 생성
     */
    public ChatRoomResponseDTO createChatRoom(Long transactionId, String buyerId, String sellerId) {
        ItemTransaction transaction = itemTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("거래를 찾을 수 없습니다."));
        Item item = transaction.getItem();

        ChatRoom room = chatRoomRepository
                .findByItemTransaction_Transactionid(transactionId)
                .orElseGet(() -> {
                    User buyer = userRepository.findById(buyerId)
                            .orElseThrow(() -> new RuntimeException("구매자 정보를 찾을 수 없습니다."));
                    User seller = userRepository.findById(sellerId)
                            .orElseThrow(() -> new RuntimeException("판매자 정보를 찾을 수 없습니다."));

                    return chatRoomRepository.save(ChatRoom.builder()
                            .itemTransaction(transaction)
                            .buyer(buyer)
                            .seller(seller)
                            .createdAt(System.currentTimeMillis())
                            .build());
                });

        // ✅ itemImages 리스트 추출
        List<String> itemImages = item.getItemImages().stream()
                .map(image -> image.getPhotoPath()) // 예: uploads/a.jpg
                .collect(Collectors.toList());

        return new ChatRoomResponseDTO(
                room.getChatroomid(),
                item.getItemid(),
                item.getTitle(),
                room.getBuyer().getUserid(),
                room.getBuyer().getName(),
                room.getSeller().getName(),
                room.getSeller().getUserid(),
                room.getCreatedAt(),
                item.getMeetLocation(),
                itemImages
        );
    }

    /**
     * 유저의 모든 채팅방 목록 조회
     */
    public List<ChatRoomResponseDTO> getChatRoomsByUser(String userId) {
        List<ChatRoom> rooms = chatRoomRepository.findByBuyer_UseridOrSeller_Userid(userId, userId);

        return rooms.stream()
                .map(room -> {
                    Item item = room.getItemTransaction().getItem();

                    List<String> itemImages = item.getItemImages().stream()
                            .map(image -> image.getPhotoPath())
                            .collect(Collectors.toList());

                    return new ChatRoomResponseDTO(
                            room.getChatroomid(),
                            item.getItemid(),
                            item.getTitle(),
                            room.getBuyer().getUserid(),
                            room.getBuyer().getName(),
                            room.getSeller().getName(),
                            room.getSeller().getUserid(),
                            room.getCreatedAt(),
                            item.getMeetLocation(),
                            itemImages
                    );
                })
                .collect(Collectors.toList());
    }

    /**
     * 단일 채팅방 상세 조회
     */
    public ChatRoom getChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));
    }

    /**
     * 채팅방 삭제
     */
    @Transactional
    public void deleteChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));

        // 1. 채팅 메시지 삭제
        chatMessageRepository.deleteAllByChatRoom_Chatroomid(chatRoomId);

        // 2. 연관된 거래 참조 해제
        chatRoom.setItemTransaction(null);

        // 3. 채팅방 삭제
        chatRoomRepository.delete(chatRoom);
    }
}
