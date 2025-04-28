package com.sutalk.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ← 이거 꼭 있어야 해!
    private Long itemid;


    private String comment;
    private String thumbnail;
    private String time;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sellerid") // nullable = false 삭제!

    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyerid")
    private User buyer;


    @Column(nullable = false)
    private String title;



    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int price;

    private String category;

    @Enumerated(EnumType.STRING)
    private Status status = Status.판매중;

    private Long regdate;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImage> images;


    public enum Status {
        판매중, 예약중, 거래완료
    }
}
