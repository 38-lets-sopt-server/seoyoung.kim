package org.sopt.repository;

import org.sopt.domain.Post;
import org.sopt.dto.response.PostResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT new org.sopt.dto.response.PostResponse(p.id, p.title, p.content, p.user.id, p.createdAt, COUNT(l)) " +
            "FROM Post p LEFT JOIN p.likes l " +
            "GROUP BY p.id, p.title, p.content, p.user.id, p.createdAt")
    List<PostResponse> findAllPostResponses();
}