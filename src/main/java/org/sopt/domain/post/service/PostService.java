package org.sopt.domain.post.service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.sopt.domain.post.entity.Post;
import org.sopt.domain.user.entity.User;
import org.sopt.domain.post.dto.CreatePostRequest;
import org.sopt.domain.post.dto.CreatePostResponse;
import org.sopt.domain.post.dto.PostResponse;
import org.sopt.domain.post.repository.PostRepository;
import org.sopt.domain.user.repository.UserRepository;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // CREATE
    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Post post = new Post(request.title(), request.content(), user);
        postRepository.save(post);
        return new CreatePostResponse(post.getId());
    }

    // READ
    @Transactional(readOnly=true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAllPostResponses();
    }

    // READ - 단건
    @Transactional(readOnly=true)
    public PostResponse getPost(Long id) {
        return postRepository.findPostResponseById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
    }

    // UPDATE
    @Transactional
    public void updatePost(Long id, String newTitle, String newContent) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        post.update(newTitle, newContent);
    }

    // DELETE
    @Transactional
    public void deletePost(Long id) {
       Post post = postRepository.findById(id)
               .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
       postRepository.delete(post);
    }
}
