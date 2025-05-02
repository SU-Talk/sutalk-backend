package org.example.sutalkbe.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.sutalkbe.entity.Image;
import org.example.sutalkbe.entity.Post;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
public class PostResponseDTO {
    private Long id;
    private String title;
    private String comment;
    private String price; // 쉼표가 포함된 문자열 (예: "1,800,000원")
    private String category;
    private String location;
    private String thumbnail;
    private String formattedCreatedAt; // 포맷된 날짜 문자열
    private List<ImageDTO> images;

    public PostResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.comment = post.getComment();
        this.price = formatPrice(post.getPrice());
        this.category = post.getCategory();
        this.location = post.getLocation();
        this.thumbnail = post.getThumbnail();
        this.formattedCreatedAt = (post.getCreatedAt() != null)
                ? post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
                : "날짜 정보 없음";
        this.images = post.getImages() != null
                ? post.getImages().stream().map(ImageDTO::new).collect(Collectors.toList())
                : null;
    }

    // 가격 포맷팅 (Null-safe)
    private String formatPrice(String price) {
        if (price == null || price.trim().isEmpty()) {
            return "가격 정보 없음";
        }
        try {
            int priceInt = Integer.parseInt(price.replaceAll("[^0-9]", ""));
            return new DecimalFormat("#,###").format(priceInt) + "원";
        } catch (NumberFormatException e) {
            return price; // 변환 실패 시 원본 값 반환
        }
    }

    @Getter @Setter
    public static class ImageDTO {
        private Long id;
        private String url;
        public ImageDTO(Image image) {
            this.id = image.getId();
            this.url = image.getUrl();
        }
    }
}
