package org.sopt.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.sopt.domain.common.BaseTimeEntity;

import java.time.LocalDateTime;

@SQLDelete(sql = "UPDATE post SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;          // 게시글 상세 화면 — 특정 게시글 식별용
    private String title;     // 목록, 상세, 글쓰기 화면 — 제목
    private String content;   // 목록(미리보기), 상세(전체) 화면 — 내용
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    protected Post() {
    } //JPA 기본 생성자

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void delete(){
        this.deletedAt=LocalDateTime.now();
    }
}