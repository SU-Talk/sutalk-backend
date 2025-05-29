package com.sutalk.backend.controller;

import com.sutalk.backend.dto.FavoriteItemDTO;
import com.sutalk.backend.service.ItemLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class ItemLikeController {

    private final ItemLikeService itemLikeService;

    // 좋아요 추가 (최신 likeCount 반환)
    @PostMapping("/{itemId}")
    public ResponseEntity<Map<String, Object>> like(
            @PathVariable Long itemId,
            @RequestParam String userId) {
        Long count = itemLikeService.likeItem(itemId, userId);
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        return ResponseEntity.ok(result);
    }

    // 좋아요 취소 (최신 likeCount 반환)
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Map<String, Object>> unlike(
            @PathVariable Long itemId,
            @RequestParam String userId) {
        Long count = itemLikeService.unlikeItem(itemId, userId);
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        return ResponseEntity.ok(result);
    }

    // 좋아요 개수 조회
    @GetMapping("/{itemId}/count")
    public ResponseEntity<Long> count(@PathVariable Long itemId) {
        return ResponseEntity.ok(itemLikeService.countLikes(itemId));
    }

    // 특정 유저가 좋아요 눌렀는지 조회
    @GetMapping("/{itemId}/is-liked")
    public ResponseEntity<Boolean> isLiked(
            @PathVariable Long itemId,
            @RequestParam String userId) {
        return ResponseEntity.ok(itemLikeService.isLiked(itemId, userId));
    }

    // 내 좋아요 목록
    @GetMapping("/my")
    public ResponseEntity<List<FavoriteItemDTO>> myFavorites(
            @RequestParam String userId) {
        return ResponseEntity.ok(itemLikeService.getUserFavorites(userId));
    }
}
