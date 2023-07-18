package com.sparta.curtain.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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
    private Long id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String username;

    @Column(name = "user_email",  nullable = false)
    private String email;

    @Column(name = "user_password",  nullable = false)
    private String password;

    @Column(name = "user_role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    //메일 인증 관련 인증키값 저장
    @Column(name = "user_authkey")
    private String authKey;

    @Column(name="user_confirmn", nullable = false)
    @ColumnDefault("false")
    private Boolean isConfirm;

    private Long kakaoId;

    public User(String username, String password, String email, UserRoleEnum role,String authKey) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.authKey=authKey;
        this.isConfirm=false;
    }

    public User(String username, String password, String email, UserRoleEnum role, Long kakaoId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.kakaoId = getKakaoId();

    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    public void setIsConfirmTrue() {
        this.isConfirm=true;
    }
}
