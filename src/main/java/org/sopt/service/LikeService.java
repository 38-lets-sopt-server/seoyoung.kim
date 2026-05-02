package org.sopt.service;

import org.sopt.domain.Like;
import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.exception.BusinessException;
import org.sopt.exception.ErrorCode;
import org.sopt.repository.LikeRepository;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public LikeService(LikeRepository likeRepository, UserRepository userRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

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

        likeRepository.save(new Like(user, post));
    }

    //좋아요 취소
    @Transactional
    public void cancelLike(Long postId, Long userId){
        Like like = likeRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.LIKE_NOT_FOUND));
        likeRepository.delete(like);
    }
}
