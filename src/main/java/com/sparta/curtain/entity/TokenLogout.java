package com.sparta.curtain.entity;


import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Entity
@Component
public class TokenLogout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;
    private String token;

    public TokenLogout() {
    }

    public TokenLogout(String token) {
        this.token = token;
    }
}
