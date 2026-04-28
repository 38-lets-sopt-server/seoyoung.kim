package org.sopt.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String emaill;

    protected User(){}

    public User(String nickname, String emaill) {
        this.nickname = nickname;
        this.emaill = emaill;
    }

    public String getEmaill() {
        return emaill;
    }

    public String getNickname() {
        return nickname;
    }

    public Long getId() {
        return id;
    }
}
