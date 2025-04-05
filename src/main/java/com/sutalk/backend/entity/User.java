// User.java
package com.sutalk.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String userid;

    private String name;

    @Column(nullable = false)
    private String email;

    private String phone;

    private String status;

    @Column(nullable = false)
    private byte[] password;

    // 관계 설정 (예: 아이템 목록)
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;
}
