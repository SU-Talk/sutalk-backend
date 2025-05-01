package com.sutalk.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.ArrayList; // ✅ 추가

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemid;

    private String comment;
    private String thumbnail;
    private String time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sellerid")
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyerid")
    private User buyer;

    @Column(nullable = false)
    private String title;

    @Column(length = 255)
    private String meetLocation;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int price;

    private String category;

    @Enumerated(EnumType.STRING)
    private Status status = Status.판매중;

    private Long regdate;

    @Builder.Default
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImage> itemImages = new ArrayList<>();

    public void addItemImage(ItemImage image) {
        this.itemImages.add(image);
        image.setItem(this);
    }

    public enum Status {
        판매중, 예약중, 거래완료
    }
}
