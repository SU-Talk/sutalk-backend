package org.example.sutalkbe.service;

import lombok.RequiredArgsConstructor;
import org.example.sutalkbe.entity.SearchHistory;
import org.example.sutalkbe.repository.SearchHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {
    private final SearchHistoryRepository searchHistoryRepository;

    public List<SearchHistory> getAllHistories() {
        return searchHistoryRepository.findAll();
    }

    public SearchHistory addHistory(String query) {
        SearchHistory history = new SearchHistory();
        history.setQuery(query);
        return searchHistoryRepository.save(history);
    }

    public void deleteHistory(Long id) {
        searchHistoryRepository.deleteById(id);
    }

    public void deleteAllHistories() {
        searchHistoryRepository.deleteAll();
    }
}
