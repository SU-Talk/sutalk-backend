package org.example.sutalkbe.entity;

import jakarta.persistence.*;
import lombok.*;

// User.java
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    // 기타 필드 추가
}
