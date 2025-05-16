package com.sutalk.backend.repository;

import com.sutalk.backend.entity.History;
import com.sutalk.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<History, Long> {

    List<History> findTop5ByUserAndKeywordContainingOrderBySearchAtDesc(User user, String keyword);

    List<History> findAllByUserOrderBySearchAtDesc(User user);

    void deleteByUserAndKeyword(User user, String keyword);

    void deleteAllByUser(User user);
}
