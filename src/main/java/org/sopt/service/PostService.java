package org.sopt.service;
import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostRepository;
import org.sopt.validator.PostValidator;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository=postRepository;
    }

    // CREATE
    public CreatePostResponse createPost(CreatePostRequest request) {
        PostValidator.validateTitle(request.title());
        String createdAt = java.time.LocalDateTime.now().toString();
        Post post = new Post(
                postRepository.generateId(),
                request.title(),
                request.content(),
                request.author(),
                createdAt);
        postRepository.save(post);
        return new CreatePostResponse(post.getId(), "게시글 등록 완료!");
    }

    // READ - 전체 📝 과제
    public List<PostResponse> getAllPosts() {
      return postRepository.findAll().stream()
              .map(PostResponse::from)
              .toList();
    }

    // READ - 단건 📝 과제
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        return PostResponse.from(post);
    }

    // UPDATE 📝 과제
    public void updatePost(Long id, String newTitle, String newContent) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        PostValidator.validateTitle(newTitle);
        post.update(newTitle, newContent);
    }

    // DELETE 📝 과제
    public void deletePost(Long id) {
       Post post = postRepository.findById(id)
               .orElseThrow(PostNotFoundException::new);
       postRepository.delete(post);
    }
}
