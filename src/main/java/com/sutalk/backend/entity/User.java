package com.sutalk.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "user")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    private String userid;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    private String status;

    private String password;

    // ✅ 순환참조 방지
    @JsonManagedReference(value = "user-seller")
    @OneToMany(mappedBy = "seller")
    private List<Item> sellingItems;

    @JsonManagedReference(value = "user-buyer")
    @OneToMany(mappedBy = "buyer")
    private List<Item> buyingItems;

    @OneToMany(mappedBy = "user")
    private List<ItemTransaction> transactions;

    @OneToMany(mappedBy = "reporter")
    private List<Report> reportsSent;

    @OneToMany(mappedBy = "reported")
    private List<Report> reportsReceived;

    @OneToMany(mappedBy = "blocker")
    private List<Block> blockedUsers;

    @OneToMany(mappedBy = "blocked")
    private List<Block> blockingUsers;

    @OneToMany(mappedBy = "sender")
    private List<ChatMessage> sentMessages;
}
