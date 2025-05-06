package com.sutalk.backend.service;

import com.sutalk.backend.entity.SearchHistory;
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

    @Transactional(readOnly = true)
    public List<String> getSearchHistory() {
        List<SearchHistory> histories = searchHistoryRepository.findAllByOrderBySearchedAtDesc();
        return histories.stream()
                .map(SearchHistory::getKeyword)
                .collect(Collectors.toList());
    }
    /**
     * 검색 기록을 추가합니다. 동일 키워드는 삭제 후 최신으로 추가합니다.
     */
    @Transactional
    public void addSearchHistory(String keyword) {
        searchHistoryRepository.deleteByKeyword(keyword);
        SearchHistory history = SearchHistory.builder()
                .keyword(keyword)
                .searchedAt(LocalDateTime.now())
                .build();
        searchHistoryRepository.save(history);
    }

    /**
     * 특정 검색 기록을 삭제합니다.
     */
    @Transactional
    public void deleteSearchHistory(String keyword) {
        searchHistoryRepository.deleteByKeyword(keyword);
    }

    /**
     * 모든 검색 기록을 삭제합니다.
     */
    @Transactional
    public void deleteAllSearchHistory() {
        searchHistoryRepository.deleteAll();
    }
}
