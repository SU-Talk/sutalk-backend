package com.sutalk.backend.repository;

import com.sutalk.backend.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByBuyer_UseridOrSeller_Userid(String buyerId, String sellerId);

    // ✅ 거래 ID만으로 중복 방 체크
    Optional<ChatRoom> findByItemTransaction_Transactionid(Long transactionId);
}
