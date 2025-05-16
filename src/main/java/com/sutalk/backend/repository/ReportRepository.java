package com.sutalk.backend.repository;

import com.sutalk.backend.entity.Report;
import com.sutalk.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    long countByReported(User reported);
}
