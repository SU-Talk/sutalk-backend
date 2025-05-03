package com.sutalk.backend.repository;

import com.sutalk.backend.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByBuyer_UseridOrSeller_Userid(String buyerId, String sellerId);
    Optional<ChatRoom> findByBuyer_UseridAndSeller_UseridAndItemTransaction_Transactionid(String buyerId, String sellerId, Long transactionId);
}
