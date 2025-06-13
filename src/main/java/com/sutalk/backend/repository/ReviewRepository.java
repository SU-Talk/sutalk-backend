package com.sutalk.backend.repository;

import com.sutalk.backend.entity.Item;
import com.sutalk.backend.entity.Review;
import com.sutalk.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sutalk.backend.dto.ReviewSummaryDTO;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByItemAndBuyer(Item item, User buyer);

    List<Review> findByReviewee_Userid(String sellerId);  // 받은 후기들
    // ✅ 상세 리뷰용 (Reviewer 이름, 아이템 제목 등 필요할 때)
    List<Review> findByReviewee_UseridOrderByCreatedAtDesc(String sellerId);

    // ✅ 평균 별점 계산용
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.reviewee = :user")
    Double findAverageRatingByUser(@Param("user") User user);

    // ✅ 후기 개수 계산용
    int countByReviewee(User user);

    @Query("SELECT new com.sutalk.backend.dto.ReviewSummaryDTO(r.comment, r.rating, r.createdAt, u.userid, i.title) " +
            "FROM Review r " +
            "JOIN r.reviewer u " +
            "JOIN r.item i " +
            "WHERE r.reviewee.userid = :sellerId " +
            "ORDER BY r.createdAt DESC")
    List<ReviewSummaryDTO> findReviewSummariesBySellerId(@Param("sellerId") String sellerId);

}