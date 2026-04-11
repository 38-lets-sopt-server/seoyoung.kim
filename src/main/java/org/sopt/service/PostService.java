package org.sopt.service;
import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostRepository;
import org.sopt.validator.PostValidator;

import java.util.ArrayList;
import java.util.List;

public class PostService {
    private final PostRepository postRepository = new PostRepository();

    // CREATE
    public CreatePostResponse createPost(CreatePostRequest request) {
        PostValidator.validateTitle(request.getTitle());
        String createdAt = java.time.LocalDateTime.now().toString();
        Post post = new Post(postRepository.generateId(), request.getTitle(), request.getContent(), request.getAuthor(), createdAt);
        postRepository.save(post);
        return new CreatePostResponse(post.getId(), "게시글 등록 완료!");
    }

    // READ - 전체 📝 과제
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        List<PostResponse> result = new ArrayList<>();
        for(Post post: posts){
            result.add(new PostResponse(post));
        }
        return result;
    }

    // READ - 단건 📝 과제
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id);

        if(post==null){
            throw new PostNotFoundException();
        }

        return new PostResponse(post);
    }

    // UPDATE 📝 과제
    public void updatePost(Long id, String newTitle, String newContent) {
        Post post = postRepository.findById(id);

        if(post==null) {
            throw new PostNotFoundException();
        }

        PostValidator.validateTitle(newTitle);

        post.update(newTitle, newContent);
    }

    // DELETE 📝 과제
    public void deletePost(Long id) {
        Post post = postRepository.findById(id);

        if(post==null){
            throw new PostNotFoundException();
        }

        postRepository.deleteById(id);
    }
}
