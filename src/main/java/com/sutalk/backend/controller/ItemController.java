package com.sutalk.backend.controller;

import com.sutalk.backend.entity.Item;
import com.sutalk.backend.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }
}
