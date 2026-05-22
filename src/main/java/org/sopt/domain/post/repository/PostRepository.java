package org.sopt.domain.post.repository;

import org.sopt.domain.post.entity.Post;
import org.sopt.domain.post.dto.PostResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT new org.sopt.domain.post.dto.PostResponse(p.id, p.title, p.content, p.user.id, p.createdAt, COUNT(l)) " +
            "FROM Post p LEFT JOIN Like l ON l.post = p " +
            "GROUP BY p.id, p.title, p.content, p.user.id, p.createdAt")
    List<PostResponse> findAllPostResponses();

    @Query("SELECT new org.sopt.domain.post.dto.PostResponse(p.id, p.title, p.content, p.user.id, p.createdAt, COUNT(l)) " +
            "FROM Post p LEFT JOIN Like l ON l.post = p " +
            "WHERE p.id = :id " +
            "GROUP BY p.id, p.title, p.content, p.user.id, p.createdAt")
    Optional<PostResponse> findPostResponseById(@Param("id") Long id);
}