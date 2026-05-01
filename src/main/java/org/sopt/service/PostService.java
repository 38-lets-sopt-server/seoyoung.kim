package org.sopt.service;
import org.springframework.transaction.annotation.Transactional;
import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.PostNotFoundException;
import org.sopt.exception.UserNotFoundException;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository){
        this.postRepository=postRepository;
        this.userRepository=userRepository;
    }

    // CREATE
    @Transactional
    public CreatePostResponse createPost(CreatePostRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(UserNotFoundException::new);

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
      return postRepository.findAll().stream()
              .map(PostResponse::from)
              .toList();
    }

    // READ - 단건
    @Transactional(readOnly=true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        return PostResponse.from(post);
    }

    // UPDATE
    @Transactional
    public void updatePost(Long id, String newTitle, String newContent) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        post.update(newTitle, newContent);
    }

    // DELETE
    @Transactional
    public void deletePost(Long id) {
       Post post = postRepository.findById(id)
               .orElseThrow(PostNotFoundException::new);
       postRepository.delete(post);
    }
}
