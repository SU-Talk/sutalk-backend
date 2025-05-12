package com.sutalk.backend.service;

import com.sutalk.backend.entity.History;
import com.sutalk.backend.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchHistoryRepository searchHistoryRepository;

    // 연관 검색어 추천
    @Transactional(readOnly = true)
    public List<String> getSuggestions(String keyword) {
        return searchHistoryRepository
                .findTop5ByKeywordContainingOrderBySearchAtDesc(keyword)
                .stream()
                .map(History::getKeyword)
                .distinct()
                .collect(Collectors.toList());
    }

    // 검색 기록 조회
    @Transactional(readOnly = true)
    public List<String> getSearchHistory() {
        List<History> histories = searchHistoryRepository.findAllByOrderBySearchAtDesc();
        return histories.stream()
                .map(History::getKeyword)
                .collect(Collectors.toList());
    }

    // 검색 기록 추가
    @Transactional
    public void addSearchHistory(String keyword) {
        searchHistoryRepository.deleteByKeyword(keyword);
        History history = History.builder()
                .keyword(keyword)
                .searchAt(LocalDateTime.now()) // 필드명에 맞춤
                .build();
        searchHistoryRepository.save(history);
    }

    // 개별 검색 기록 삭제
    @Transactional
    public void deleteSearchHistory(String keyword) {
        searchHistoryRepository.deleteByKeyword(keyword);
    }

    // 전체 검색 기록 삭제
    @Transactional
    public void deleteAllSearchHistory() {
        searchHistoryRepository.deleteAll();
    }
}
