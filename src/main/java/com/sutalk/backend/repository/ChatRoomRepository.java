package com.sutalk.backend.repository;

import com.sutalk.backend.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByBuyer_UseridOrSeller_Userid(String buyerId, String sellerId);
    List<ChatRoom> findAllByItemTransaction_Transactionid(Long transactionId);

    Optional<ChatRoom> findByItemTransaction_Transactionid(Long transactionId);

    void deleteByItemTransaction_Transactionid(Long transactionId); // ✅ 이거 추가!

    @Query("SELECT cr FROM ChatRoom cr WHERE cr.seller.userid = :userId OR cr.buyer.userid = :userId")
    List<ChatRoom> findByUserId(@Param("userId") String userId);
}
