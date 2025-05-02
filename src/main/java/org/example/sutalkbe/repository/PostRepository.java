package org.example.sutalkbe.repository;

import org.example.sutalkbe.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // 명확한 JOIN FETCH 쿼리 작성
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.images WHERE p.id = :id")
    Optional<Post> findByIdWithImages(@Param("id") Long id);
}