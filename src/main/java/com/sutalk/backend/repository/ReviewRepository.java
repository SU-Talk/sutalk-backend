package com.sutalk.backend.repository;

import com.sutalk.backend.entity.Item;
import com.sutalk.backend.entity.Review;
import com.sutalk.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByItemAndBuyer(Item item, User buyer);
}
