package com.sutalk.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    private String transactionid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemid", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyerid", nullable = false)
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sellerid", nullable = false)
    private User seller;

    @Enumerated(EnumType.STRING)
    private Status status = Status.대기;

    private Long regdate;

    private Long completedate;

    public enum Status {
        대기, 예약, 결제완료, 배송중, 완료
    }
}
