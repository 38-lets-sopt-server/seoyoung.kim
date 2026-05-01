package org.sopt.service;
import org.sopt.repository.LikeRepository;
import org.springframework.transaction.annotation.Transactional;
import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.sopt.exception.BusinessException;
import org.sopt.exception.ErrorCode;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, LikeRepository likeRepository){
        this.postRepository=postRepository;
        this.userRepository=userRepository;
        this.likeRepository=likeRepository;
    }

    // CREATE
    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Post post = new Post(

                request.title(),
                request.content(),
                user);
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
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
        return PostResponse.from(post, likeRepository.countByPostId(id));
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
