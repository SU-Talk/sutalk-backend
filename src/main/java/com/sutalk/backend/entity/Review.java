package com.sutalk.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
public class Review {

    @Id
    private String reviewid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transactionid", nullable = false)
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewerid", nullable = false)
    private User reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revieweeid", nullable = false)
    private User reviewee;

    @Column(nullable = false)
    private int rating; // 1~5

    @Column(columnDefinition = "TEXT")
    private String comment;

    private Long regdate;
}
