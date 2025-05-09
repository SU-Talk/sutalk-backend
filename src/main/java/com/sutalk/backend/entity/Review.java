package com.sutalk.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter // ✅ 추가!
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_itemid", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "buyer_userid", nullable = false)
    private User buyer;

    @Column(nullable = false)
    private int rating; // 1~5점

    @Column(length = 500)
    private String comment;

    private LocalDateTime createdAt = LocalDateTime.now();
}
