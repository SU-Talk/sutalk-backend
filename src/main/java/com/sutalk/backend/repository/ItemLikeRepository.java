package com.sutalk.backend.repository;

import com.sutalk.backend.entity.Item;
import com.sutalk.backend.entity.ItemLike;
import com.sutalk.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemLikeRepository extends JpaRepository<ItemLike, Long> {
    Optional<ItemLike> findByItemAndUser(Item item, User user);
    Long countByItem(Item item);
    List<ItemLike> findByUser(User user);
    boolean existsByItemAndUser(Item item, User user);
    void deleteByItemAndUser(Item item, User user);

    // ✅ itemid 기준으로 좋아요 전부 삭제
    @Modifying
    @Query("DELETE FROM ItemLike il WHERE il.item.itemid = :itemid")
    void deleteByItemId(@Param("itemid") Long itemid);
}
