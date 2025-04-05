package com.sutalk.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "report")
public class Report {

    @Id
    private String reportid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporterid", nullable = false)
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportedid")
    private User reported;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemid")
    private Item item;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Enumerated(EnumType.STRING)
    private Status status = Status.접수;

    private Long regdate;

    public enum Status {
        접수, 처리중, 완료
    }
}
