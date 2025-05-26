package com.sutalk.backend.repository;

import com.sutalk.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    // ✅ userid 직접 업데이트 (native query)
    @Modifying
    @Query(value = "UPDATE user SET userid = :newId WHERE userid = :oldId", nativeQuery = true)
    void updateUserId(@Param("oldId") String oldId, @Param("newId") String newId);
}
