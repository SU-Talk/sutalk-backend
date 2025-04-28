package org.example.sutalkbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sutalkbe.entity.Message;
import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatRoomIdOrderBySentAtAsc(Long chatRoomId);
}