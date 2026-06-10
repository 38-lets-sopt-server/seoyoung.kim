package org.sopt.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private Long kakaoId;

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
}
