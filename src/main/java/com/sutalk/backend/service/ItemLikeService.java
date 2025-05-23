package com.sutalk.backend.service;

import com.sutalk.backend.dto.FavoriteItemDTO;
import com.sutalk.backend.entity.Item;
import com.sutalk.backend.entity.ItemLike;
import com.sutalk.backend.entity.User;
import com.sutalk.backend.repository.ItemLikeRepository;
import com.sutalk.backend.repository.ItemRepository;
import com.sutalk.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemLikeService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemLikeRepository itemLikeRepository;

    @Transactional
    public void likeItem(Long itemId, String userId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (itemLikeRepository.existsByItemAndUser(item, user)) return;

        ItemLike like = new ItemLike();
        like.setItem(item);
        like.setUser(user);
        itemLikeRepository.save(like);
    }

    @Transactional
    public void unlikeItem(Long itemId, String userId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<ItemLike> likeOpt = itemLikeRepository.findByItemAndUser(item, user);
        likeOpt.ifPresent(itemLikeRepository::delete);
    }

    public boolean isLiked(Long itemId, String userId) {
        return itemLikeRepository.existsByItemAndUser(
                itemRepository.findById(itemId).orElseThrow(),
                userRepository.findById(userId).orElseThrow()
        );
    }

    public Long countLikes(Long itemId) {
        return itemLikeRepository.countByItem(
                itemRepository.findById(itemId).orElseThrow()
        );
    }

    public List<FavoriteItemDTO> getUserFavorites(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ItemLike> likes = itemLikeRepository.findByUser(user);

        return likes.stream()
                .map(like -> {
                    Item item = like.getItem();
                    Long count = itemLikeRepository.countByItem(item);
                    List<String> imagePaths = item.getItemImages().stream()
                            .map(image -> image.getPhotoPath())
                            .toList();

                    return new FavoriteItemDTO(
                            item.getItemid(),
                            item.getTitle(),
                            item.getPrice(),
                            count,
                            imagePaths
                    );
                })
                .toList();
    }
}
