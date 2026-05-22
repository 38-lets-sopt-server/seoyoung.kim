package org.sopt.domain.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String email;
    private String password;

    @Column(unique = true)
    private Long kakaoId;

    protected User() {}

    public User(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public static User ofKakao(Long kakaoId, String nickname, String email) {
        User user = new User();
        user.kakaoId = kakaoId;
        user.nickname = nickname;
        user.email = email;
        return user;
    }

    public Long getId() { return id; }
    public String getNickname() { return nickname; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Long getKakaoId() { return kakaoId; }
}
