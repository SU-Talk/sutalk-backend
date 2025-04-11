package com.sutalk.backend.controller;

import com.sutalk.backend.entity.Item;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/items")
@CrossOrigin
public class ItemController {

    @GetMapping
    public List<Item> getAllItems() {
        List<Item> list = new ArrayList<>();
        Item item = Item.builder()
                .itemid(1L)
                .title("맥북 M2 팝니다")
                .comment("거의 새 상품!")
                .price(1200000)
                .thumbnail("/assets/default-image.png")
                .category("전자제품")
                .time("5분 전")
                .build();
        list.add(item);
        return list;
    }

}


