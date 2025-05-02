package com.sutalk.backend.controller;

import com.sutalk.backend.dto.ItemRegisterRequestDTO;
import com.sutalk.backend.dto.ItemResponseDTO;
import com.sutalk.backend.entity.Item;
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

    // ✅ 1. 게시글 등록
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> registerItem(
            @RequestPart("item") ItemRegisterRequestDTO requestDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        System.out.println("📩 registerItem() 호출됨 - title: " + requestDTO.getTitle());

        Long itemId = itemService.saveItemWithImages(requestDTO, images);

        Map<String, Object> response = new HashMap<>();
        response.put("itemid", itemId);
        response.put("message", "상품이 성공적으로 등록되었습니다.");
        return ResponseEntity.ok(response);
    }


    // ✅ 2. 게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> getItem(@PathVariable Long id) {
        Item item = itemService.getItemById(id);
        ItemResponseDTO responseDTO = itemService.toResponseDTO(item);
        return ResponseEntity.ok(responseDTO);
    }

    // ✅ 3. 게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> getAllItems() {
        List<ItemResponseDTO> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }
}
