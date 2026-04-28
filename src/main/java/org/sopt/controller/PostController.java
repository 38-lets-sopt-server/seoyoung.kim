package org.sopt.controller;

import jakarta.validation.Valid;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.BaseResponse;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // POST /posts
    @PostMapping
    public ResponseEntity<BaseResponse<CreatePostResponse>> createPost(
           @Valid @RequestBody CreatePostRequest request
    ) {
        CreatePostResponse response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response, "게시글 등록 성공"));
    }

    // GET /posts 📝 과제
    @GetMapping
    public ResponseEntity<BaseResponse<List<PostResponse>>> getAllPosts() {
        List<PostResponse> responses = postService.getAllPosts();
        return ResponseEntity.ok(BaseResponse.success(responses, "게시글 목록 조회 성공"));

    }

    // GET /posts/{id} 📝 과제
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> getPost(
            @PathVariable Long id
    ) {
        PostResponse response = postService.getPost(id);
        return ResponseEntity.ok(BaseResponse.success(response, "게시글 조회 성공"));
    }

    // PUT /posts/{id} 📝 과제
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePostRequest request
    ) {
        postService.updatePost(id, request.title(), request.content());
        return ResponseEntity.ok(BaseResponse.success(null, "게시글 수정 성공"));
    }

    // DELETE /posts/{id} 📝 과제
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deletePost(
            @PathVariable Long id
    ) {
        postService.deletePost(id);
        return ResponseEntity.ok(BaseResponse.success(null, "게시글 삭제 성공"));
    }
}