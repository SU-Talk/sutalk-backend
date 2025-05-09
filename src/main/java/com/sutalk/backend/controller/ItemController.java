package com.sutalk.backend.controller;

import com.sutalk.backend.dto.ItemRegisterRequestDTO;
import com.sutalk.backend.dto.ItemResponseDTO;
import com.sutalk.backend.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> registerItem(
            @RequestPart("item") ItemRegisterRequestDTO requestDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        Long itemId = itemService.saveItemWithImages(requestDTO, images);
        Map<String, Object> response = new HashMap<>();
        response.put("itemid", itemId);
        response.put("message", "상품이 성공적으로 등록되었습니다.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> getItem(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemResponseById(id));
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    // ✨ 판매자 본인 글만 조회
    @GetMapping("/mine")
    public ResponseEntity<List<ItemResponseDTO>> getMyItems(@RequestParam String userId) {
        return ResponseEntity.ok(itemService.getItemsBySellerId(userId));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> updateItem(
            @PathVariable Long id,
            @RequestPart("item") ItemRegisterRequestDTO requestDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        itemService.updateItem(id, requestDTO, images);
        Map<String, Object> response = new HashMap<>();
        response.put("itemid", id);
        response.put("message", "게시글이 성공적으로 수정되었습니다.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "게시글이 삭제되었습니다.");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Map<String, String>> updateItemStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        String status = body.get("status");
        itemService.updateItemStatus(id, status);
        Map<String, String> response = new HashMap<>();
        response.put("message", "게시글 상태가 성공적으로 변경되었습니다.");
        return ResponseEntity.ok(response);
    }




}
