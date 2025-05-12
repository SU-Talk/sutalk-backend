package com.sutalk.backend.service;

import com.sutalk.backend.dto.ItemRegisterRequestDTO;
import com.sutalk.backend.dto.ItemResponseDTO;
import com.sutalk.backend.entity.*;
import com.sutalk.backend.repository.ChatRoomRepository;
import com.sutalk.backend.repository.ChatMessageRepository;
import com.sutalk.backend.repository.ItemImageRepository;
import com.sutalk.backend.repository.ItemRepository;
import com.sutalk.backend.repository.ItemTransactionRepository;
import com.sutalk.backend.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ChatMessageRepository chatMessageRepository;
    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ItemTransactionRepository itemTransactionRepository;

    @PersistenceContext
    private EntityManager em;

    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 게시글이 존재하지 않습니다."));
    }

    public ItemResponseDTO getItemResponseById(Long id) {
        Item item = itemRepository.findItemWithSellerAndImagesById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 게시글이 존재하지 않습니다."));
        return toResponseDTO(item);
    }

    public List<ItemResponseDTO> getAllItems() {
        return itemRepository.findAllWithSellerAndImages().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ItemResponseDTO> getItemsBySellerId(String userId) {
        return itemRepository.findBySellerUserIdWithImages(userId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ItemResponseDTO toResponseDTO(Item item) {
        return ItemResponseDTO.builder()
                .itemid(item.getItemid())
                .title(item.getTitle())
                .description(item.getDescription())
                .price(item.getPrice())
                .category(item.getCategory())
                .meetLocation(item.getMeetLocation())
                .regdate(String.valueOf(item.getRegdate()))
                .sellerId(item.getSeller() != null ? item.getSeller().getUserid() : null)
                .status(item.getStatus().name())
                .itemImages(item.getItemImages() != null
                        ? item.getItemImages().stream().map(ItemImage::getPhotoPath).toList()
                        : new ArrayList<>())
                .build();
    }

    public Long saveItemWithImages(ItemRegisterRequestDTO requestDTO, List<MultipartFile> images) {
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

        if (images != null && !images.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            Path uploadPath = Paths.get(uploadDir);
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("디렉토리 생성 실패: " + e.getMessage());
            }

            for (MultipartFile file : images) {
                try {
                    String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                    String saveFileName = UUID.randomUUID().toString().replace("-", "") + ext;
                    Path filePath = uploadPath.resolve(saveFileName);
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    ItemImage image = ItemImage.builder()
                            .photoPath("/uploads/" + saveFileName)
                            .regdate(LocalDateTime.now())
                            .build();
                    item.addItemImage(image);
                } catch (IOException e) {
                    throw new RuntimeException("이미지 저장 실패: " + e.getMessage());
                }
            }
        }

        return itemRepository.save(item).getItemid();
    }

    public void updateItem(Long itemId, ItemRegisterRequestDTO requestDTO, List<MultipartFile> images) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 게시글이 존재하지 않습니다."));

        item.setTitle(requestDTO.getTitle());
        item.setDescription(requestDTO.getDescription());
        item.setPrice(requestDTO.getPrice());
        item.setCategory(requestDTO.getCategory());
        item.setMeetLocation(requestDTO.getMeetLocation());

        item.getItemImages().clear();

        if (images != null && !images.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            Path uploadPath = Paths.get(uploadDir);
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException("디렉토리 생성 실패: " + e.getMessage());
            }

            for (MultipartFile file : images) {
                try {
                    String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                    String saveFileName = UUID.randomUUID().toString().replace("-", "") + ext;
                    Path filePath = uploadPath.resolve(saveFileName);
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    ItemImage image = ItemImage.builder()
                            .photoPath("/uploads/" + saveFileName)
                            .regdate(LocalDateTime.now())
                            .build();
                    item.addItemImage(image);
                } catch (IOException e) {
                    throw new RuntimeException("이미지 저장 실패: " + e.getMessage());
                }
            }
        }
    }

    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 게시글이 존재하지 않습니다."));

        // 거래 내역 조회
        List<ItemTransaction> transactions = itemTransactionRepository.findAllByItem_Itemid(itemId);

        for (ItemTransaction transaction : transactions) {
            // 채팅방 조회 및 메시지 삭제
            List<ChatRoom> chatRooms = chatRoomRepository.findAllByItemTransaction_Transactionid(transaction.getTransactionid());
            for (ChatRoom chatRoom : chatRooms) {
                chatMessageRepository.deleteAllByChatRoom_Chatroomid(chatRoom.getChatroomid());
            }
            chatRoomRepository.deleteAll(chatRooms);
        }

        itemTransactionRepository.deleteAll(transactions);

        // 이미지도 함께 삭제
        itemImageRepository.deleteAll(item.getItemImages());

        // 최종 게시글 삭제
        itemRepository.delete(item);
    }


    public void updateItemStatus(Long itemId, String status) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 게시글이 존재하지 않습니다."));

        try {
            Item.Status newStatus = Item.Status.valueOf(status); // ENUM 변환
            item.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("유효하지 않은 상태 값입니다: " + status);
        }
    }

    // ✨ 구매자 기준 거래완료 글 목록
    public List<ItemResponseDTO> getCompletedItemsByBuyer(String buyerId) {
        return itemRepository.findCompletedByBuyerUserId(buyerId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }



}
