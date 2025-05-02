package com.sutalk.backend.repository;

import com.sutalk.backend.entity.ItemTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTransactionRepository extends JpaRepository<ItemTransaction, Long> {
}
