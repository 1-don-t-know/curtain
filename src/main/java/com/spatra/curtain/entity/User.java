package com.spatra.curtain.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.parameters.P;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "user") //유저아이디, 이메일,패스워드
// FK 댓글아이디, 게시글아이디
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "user_email",  nullable = false)
    private String email;

    @Column(name = "user_password",  nullable = false)
    private String password;

    @Column(name = "user_role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    //메일 인증 관련 인증키값 저장
    @Column(name = "user_authkey")
    private String authkey;

    @Column(name="user_confirmn", nullable = false)
    @ColumnDefault("false")
    private Boolean isConfirm;

    public User(String email, String password, UserRoleEnum role, String authkey, Boolean isConfirm) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.authkey = authkey;
        this.isConfirm = false;
    }
}
