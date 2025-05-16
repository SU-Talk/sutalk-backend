package com.sutalk.backend.repository;

import com.sutalk.backend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT DISTINCT i FROM Item i " +
            "LEFT JOIN FETCH i.seller " +
            "LEFT JOIN FETCH i.itemImages")
    List<Item> findAllWithSellerAndImages();

    @Query("SELECT i FROM Item i " +
            "LEFT JOIN FETCH i.seller " +
            "LEFT JOIN FETCH i.itemImages " +
            "WHERE i.itemid = :id")
    Optional<Item> findItemWithSellerAndImagesById(@Param("id") Long id);

    // 기존 판매자용 유지
    @Query("SELECT DISTINCT i FROM Item i " +
            "LEFT JOIN FETCH i.seller s " +
            "LEFT JOIN FETCH i.itemImages " +
            "WHERE s.userid = :userId")
    List<Item> findBySellerUserIdWithImages(@Param("userId") String userId);

    // ✅ 구매자 기준 거래완료된 아이템 조회
    @Query("SELECT DISTINCT i FROM Item i " +
            "LEFT JOIN FETCH i.seller " +
            "LEFT JOIN FETCH i.itemImages " +
            "WHERE i.buyer.userid = :buyerId AND i.status = '거래완료'")
    List<Item> findCompletedByBuyerUserId(@Param("buyerId") String buyerId);

    @Query("SELECT DISTINCT i FROM Item i " +
            "WHERE LOWER(i.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(i.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "ORDER BY i.regdate DESC")
    List<Item> findTop10ByKeyword(@Param("keyword") String keyword);

}
