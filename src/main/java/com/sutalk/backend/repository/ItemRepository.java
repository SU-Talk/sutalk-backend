package com.sutalk.backend.repository;

import com.sutalk.backend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
