package com.sutalk.backend.service;

import com.sutalk.backend.dto.ItemRegisterRequestDTO;
import com.sutalk.backend.entity.Item;
import com.sutalk.backend.entity.ItemImage;
import com.sutalk.backend.entity.User;
import com.sutalk.backend.repository.ItemRepository;
import com.sutalk.backend.repository.ItemImageRepository;
import com.sutalk.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;



import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final UserRepository userRepository;  // seller 찾기 위해 필요

    public Long saveItem(ItemRegisterRequestDTO requestDTO) {
        // 1. 판매자 유저 찾기 (더미 유저)
        User seller = userRepository.findById(requestDTO.getSellerId())
                .orElseThrow(() -> new IllegalArgumentException("판매자 정보를 찾을 수 없습니다."));

        // 2. Item 엔티티 생성
        Item item = Item.builder()
                .seller(seller)
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .price(requestDTO.getPrice())
                .category(requestDTO.getCategory())
                .status(Item.Status.판매중)  // enum 기본값
                .build();

        // 3. Item 저장
        itemRepository.save(item);

        // 4. ItemImage 저장
        List<ItemImage> itemImages = requestDTO.getItemImages().stream()
                .map(photoPath -> ItemImage.builder()
                        .item(item)
                        .photoPath(photoPath)
                        .regdate(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        itemImageRepository.saveAll(itemImages);

        // 5. 등록 완료 후 ItemID 반환
        return item.getItemid();  // Long 타입으로 반환
    }

}
