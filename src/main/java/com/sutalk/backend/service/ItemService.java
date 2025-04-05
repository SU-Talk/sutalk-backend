package com.sutalk.backend.service;

import com.sutalk.backend.entity.Item;
import com.sutalk.backend.entity.User;
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
        // 요청에서 넘어온 seller는 transient 상태이므로 DB에서 영속 객체로 다시 조회해야 함
        String sellerId = item.getSeller().getUserid();
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("해당 판매자가 존재하지 않습니다."));

        item.setSeller(seller); // 영속된 seller로 교체

        return itemRepository.save(item);
    }
}
