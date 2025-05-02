package com.sutalk.backend.repository;

import com.sutalk.backend.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    // 필요 시 확장: 구매자/판매자 기준 채팅방 조회 등
}
