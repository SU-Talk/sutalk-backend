package com.sutalk.backend.service;

import com.sutalk.backend.dto.ReportRequestDTO;
import com.sutalk.backend.entity.Item;
import com.sutalk.backend.entity.Report;
import com.sutalk.backend.entity.User;
import com.sutalk.backend.repository.ItemRepository;
import com.sutalk.backend.repository.ReportRepository;
import com.sutalk.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public void submitReport(ReportRequestDTO dto) {
        User reporter = userRepository.findById(dto.getReporterId())
                .orElseThrow(() -> new RuntimeException("신고자 없음"));

        User reported = userRepository.findById(dto.getReportedId())
                .orElseThrow(() -> new RuntimeException("피신고자 없음"));

        Item item = itemRepository.findById(dto.getItemId()).orElse(null);

        Report report = Report.builder()
                .reporter(reporter)
                .reported(reported)
                .item(item)
                .reason(dto.getReason())
                .regdate(System.currentTimeMillis())
                .status(Report.Status.접수) // 🔥 명시적으로 기본 상태 지정
                .build();

        reportRepository.save(report);

        long count = reportRepository.countByReported(reported);
        if (count >= 3) {
            // 향후 징계 처리 로직 연결 가능
        }
    }
}
