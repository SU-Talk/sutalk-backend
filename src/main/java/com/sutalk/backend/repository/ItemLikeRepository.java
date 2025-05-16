package com.sutalk.backend.repository;

import com.sutalk.backend.entity.Item;
import com.sutalk.backend.entity.ItemLike;
import com.sutalk.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemLikeRepository extends JpaRepository<ItemLike, Long> {
    Optional<ItemLike> findByItemAndUser(Item item, User user);
    Long countByItem(Item item);
    List<ItemLike> findByUser(User user);
    boolean existsByItemAndUser(Item item, User user);
    void deleteByItemAndUser(Item item, User user);
}
