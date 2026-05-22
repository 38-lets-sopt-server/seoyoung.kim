package org.sopt.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.post.entity.Like;
import org.sopt.domain.post.entity.Post;
import org.sopt.domain.user.entity.User;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.sopt.domain.post.repository.LikeRepository;
import org.sopt.domain.post.repository.PostRepository;
import org.sopt.domain.user.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 좋아요 추가
    @Transactional
    public void addLike(Long postId, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        if(likeRepository.existsByUserIdAndPostId(userId, postId)){
            throw new BusinessException(ErrorCode.LIKE_ALREADY_EXISTS);
        }

        try {
            likeRepository.save(new Like(user, post));
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(ErrorCode.LIKE_ALREADY_EXISTS);
        }
    }

    //좋아요 취소
    @Transactional
    public void cancelLike(Long postId, Long userId){
        Like like = likeRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.LIKE_NOT_FOUND));
        likeRepository.delete(like);
    }
}
