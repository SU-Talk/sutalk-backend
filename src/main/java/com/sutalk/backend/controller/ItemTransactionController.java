// 📁 controller/ItemTransactionController.java
package com.sutalk.backend.controller;

import com.sutalk.backend.dto.TransactionRequestDTO;
import com.sutalk.backend.entity.ItemTransaction;
import com.sutalk.backend.service.ItemTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class ItemTransactionController {

    private final ItemTransactionService itemTransactionService;

    @PostMapping
    public ResponseEntity<ItemTransaction> createTransaction(@RequestBody TransactionRequestDTO dto) {
        ItemTransaction transaction = itemTransactionService.createTransaction(
                dto.getBuyerId(), dto.getSellerId(), dto.getItemId());
        return ResponseEntity.ok(transaction);
    }
}
