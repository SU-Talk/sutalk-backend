package com.sutalk.backend.controller;

import com.sutalk.backend.dto.TransactionRequestDTO;
import com.sutalk.backend.entity.Item;
import com.sutalk.backend.entity.ItemTransaction;
import com.sutalk.backend.entity.User;
import com.sutalk.backend.repository.ItemRepository;
import com.sutalk.backend.repository.UserRepository;
import com.sutalk.backend.repository.ItemTransactionRepository;
import com.sutalk.backend.service.ItemTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class ItemTransactionController {

    private final ItemTransactionService itemTransactionService;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemTransactionRepository itemTransactionRepository;

    // 🔸 거래 생성
    @PostMapping
    public ResponseEntity<ItemTransaction> createTransaction(@RequestBody TransactionRequestDTO dto) {
        ItemTransaction transaction = itemTransactionService.createTransaction(
                dto.getBuyerId(), dto.getSellerId(), dto.getItemId());
        return ResponseEntity.ok(transaction);
    }

    // 🔸 거래 ID 조회 (프론트에서 후기 작성 시 필요)
    @GetMapping("/item/{itemId}/user/{userId}")
    public ResponseEntity<Map<String, Long>> getTransactionId(
            @PathVariable long itemId,
            @PathVariable String userId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return itemTransactionRepository.findByItemAndUser(item, user)
                .map(transaction ->
                        ResponseEntity.ok(Map.of("transactionId", transaction.getTransactionid()))
                )
                .orElse(ResponseEntity.badRequest().build());
    }

}
