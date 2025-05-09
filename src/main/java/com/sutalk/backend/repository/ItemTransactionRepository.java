package com.sutalk.backend.repository;

import com.sutalk.backend.entity.Item;
import com.sutalk.backend.entity.ItemTransaction;
import com.sutalk.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemTransactionRepository extends JpaRepository<ItemTransaction, Long> {

    List<ItemTransaction> findByItem(Item item);
    List<ItemTransaction> findAllByItem_Itemid(Long itemId);

    // 🔹 거래 ID 조회용 추가
    Optional<ItemTransaction> findByItemAndUser(Item item, User user);
}
