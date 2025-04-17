package com.sutalk.backend.service;

import com.sutalk.backend.entity.Item;      // ✅ 요거!
import com.sutalk.backend.entity.User;      // ✅ 요것도!
import com.sutalk.backend.repository.ItemRepository;
import com.sutalk.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item createItem(Item item) {
        String sellerId = item.getSeller().getUserid();
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("해당 판매자가 존재하지 않습니다."));

        item.setSeller(seller);
        return itemRepository.save(item);
    }
}
