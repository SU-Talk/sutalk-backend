package com.sutalk.backend.repository;

import com.sutalk.backend.entity.Item;
import com.sutalk.backend.entity.ItemTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemTransactionRepository extends JpaRepository<ItemTransaction, Long> {
    List<ItemTransaction> findByItem(Item item);
    List<ItemTransaction> findAllByItem_Itemid(Long itemId);
}
