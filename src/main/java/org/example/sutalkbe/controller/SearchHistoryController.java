package org.example.sutalkbe.controller;

import lombok.RequiredArgsConstructor;
import org.example.sutalkbe.entity.SearchHistory;
import org.example.sutalkbe.service.SearchHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search-history")
@RequiredArgsConstructor
public class SearchHistoryController {
    private final SearchHistoryService searchHistoryService;

    @GetMapping
    public List<SearchHistory> getAllHistories() {
        return searchHistoryService.getAllHistories();
    }

    @PostMapping
    public SearchHistory addHistory(@RequestBody SearchHistory history) {
        return searchHistoryService.addHistory(history.getQuery());
    }

    @DeleteMapping("/{id}")
    public void deleteHistory(@PathVariable Long id) {
        searchHistoryService.deleteHistory(id);
    }

    @DeleteMapping
    public void deleteAllHistories() {
        searchHistoryService.deleteAllHistories();
    }
}
