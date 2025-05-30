package com.sutalk.backend.controller;

import com.sutalk.backend.dto.FavoriteItemDTO;
import com.sutalk.backend.service.ItemLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class ItemLikeController {

    private final ItemLikeService itemLikeService;

    // 좋아요 추가 (응답은 Void, 프론트는 likeCount 직접 요청해서 반영)
    @PostMapping("/{itemId}")
    public ResponseEntity<Void> like(@PathVariable Long itemId, @RequestParam String userId) {
        itemLikeService.likeItem(itemId, userId);
        return ResponseEntity.ok().build();
    }

    // 좋아요 취소 (응답은 Void)
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> unlike(@PathVariable Long itemId, @RequestParam String userId) {
        itemLikeService.unlikeItem(itemId, userId);
        return ResponseEntity.ok().build();
    }

    // 좋아요 개수 조회
    @GetMapping("/{itemId}/count")
    public ResponseEntity<Long> count(@PathVariable Long itemId) {
        return ResponseEntity.ok(itemLikeService.countLikes(itemId));
    }

    // 특정 유저가 좋아요 눌렀는지 조회
    @GetMapping("/{itemId}/is-liked")
    public ResponseEntity<Boolean> isLiked(@PathVariable Long itemId, @RequestParam String userId) {
        return ResponseEntity.ok(itemLikeService.isLiked(itemId, userId));
    }

    // 특정 유저의 좋아요 목록
    @GetMapping("/my")
    public ResponseEntity<List<FavoriteItemDTO>> myFavorites(@RequestParam String userId) {
        return ResponseEntity.ok(itemLikeService.getUserFavorites(userId));
    }
}
