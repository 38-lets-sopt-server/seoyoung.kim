package org.sopt.repository;

import org.sopt.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);
    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);
    long countByPostId(Long postId);
}
