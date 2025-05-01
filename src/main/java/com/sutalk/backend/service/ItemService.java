package com.sutalk.backend.service;

import com.sutalk.backend.dto.ItemRegisterRequestDTO;
import com.sutalk.backend.dto.ItemResponseDTO;
import com.sutalk.backend.entity.Item;
import com.sutalk.backend.entity.ItemImage;
import com.sutalk.backend.entity.User;
import com.sutalk.backend.repository.ItemRepository;
import com.sutalk.backend.repository.ItemImageRepository;
import com.sutalk.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final UserRepository userRepository;

    // ✅ 상세 조회용
    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 게시글이 존재하지 않습니다."));
    }

    // ✅ 상세페이지 및 리스트에서 쓰일 변환 메서드
    public ItemResponseDTO toResponseDTO(Item item) {
        return ItemResponseDTO.builder()
                .itemid(item.getItemid())
                .title(item.getTitle())
                .description(item.getDescription())
                .price(item.getPrice())
                .category(item.getCategory())
                .sellerId(item.getSeller().getUserid())
                .meetLocation(item.getMeetLocation())
                .regdate(String.valueOf(item.getRegdate()))
                .itemImages(
                        item.getItemImages() != null
                                ? item.getItemImages().stream()
                                .map(ItemImage::getPhotoPath)
                                .collect(Collectors.toList())
                                : new ArrayList<>()
                )
                .build();
    }

    // ✅ 등록
    public Long saveItem(ItemRegisterRequestDTO requestDTO) {
        User seller = userRepository.findById(requestDTO.getSellerId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));


        Item item = Item.builder()
                .seller(seller)
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .price(requestDTO.getPrice())
                .category(requestDTO.getCategory())
                .status(Item.Status.판매중)
                .meetLocation(requestDTO.getMeetLocation())
                .regdate(System.currentTimeMillis())
                .build();

        for (String photoPath : requestDTO.getItemImages()) {
            ItemImage image = ItemImage.builder()
                    .photoPath(photoPath)
                    .regdate(LocalDateTime.now())
                    .build();
            item.addItemImage(image);
        }

        System.out.println("📩 저장 직전 item = " + item.getTitle() + ", seller = " + seller.getUserid());
        Item saved = itemRepository.save(item);
        System.out.println("✅ 저장 완료 itemId = " + saved.getItemid());

        return saved.getItemid();
    }


    // ✅ 전체 목록 반환
    public List<ItemResponseDTO> getAllItems() {
        return itemRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}
