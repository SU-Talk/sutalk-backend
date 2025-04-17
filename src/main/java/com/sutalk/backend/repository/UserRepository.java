package com.sutalk.backend.repository;

import com.sutalk.backend.entity.User; // ✅ 요거 중요!
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
