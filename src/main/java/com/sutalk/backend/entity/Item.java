package com.sutalk.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemid;

    private String title;

    private String comment;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int price;

    private String category;

    private String status;

    private LocalDateTime regdate;

    private LocalDateTime completedDate;

    private String thumbnail;

    private String time;

    @ManyToOne
    @JoinColumn(name = "seller_userid")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer_userid")
    private User buyer;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemImage> images;
}
