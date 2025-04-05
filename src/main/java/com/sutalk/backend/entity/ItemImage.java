package com.sutalk.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_images")
public class ItemImage {

    @Id
    private String imageid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemid", nullable = false)
    private Item item;

    @Column(nullable = false)
    private String src; // AWS S3 URL

    private Long regdate;
}
