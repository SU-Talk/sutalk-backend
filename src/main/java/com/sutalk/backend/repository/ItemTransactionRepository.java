package com.sutalk.backend.repository;

import com.sutalk.backend.entity.ItemTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemTransactionRepository extends JpaRepository<ItemTransaction, Long> {

    List<ItemTransaction> findAllByItem_ItemidAndUser_UseridAndDistinctSellerNot(Long itemId, String userId, String distinctSeller);

    Optional<ItemTransaction> findByItem_ItemidAndUser_UseridAndDistinctSellerNot(Long itemId, String buyerId, String buyerIdAgain);

    List<ItemTransaction> findAllByItem_Itemid(Long itemId);

    // ✅ FK 업데이트용
    @Modifying
    @Query("UPDATE ItemTransaction t SET t.user.userid = :newId WHERE t.user.userid = :oldId")
    void updateUserId(@Param("oldId") String oldId, @Param("newId") String newId);
}
