package com.sutalk.backend.controller;

import com.sutalk.backend.dto.ItemRegisterRequestDTO;
import com.sutalk.backend.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<?> registerItem(@RequestBody ItemRegisterRequestDTO requestDTO) {
        Long itemId = itemService.saveItem(requestDTO);
        return ResponseEntity.ok().body(
                new ItemRegisterResponse(itemId, "상품이 성공적으로 등록되었습니다.")
        );
    }

    @Getter
    @AllArgsConstructor
    static class ItemRegisterResponse {
        private Long itemId;
        private String message;
    }
}
