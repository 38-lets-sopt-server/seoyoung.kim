package org.sopt.controller;

import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.ApiResponse;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.exception.PostNotFoundException;
import org.sopt.service.PostService;

import java.util.List;

public class PostController {
    private final PostService postService = new PostService();

    // POST /posts
    public ApiResponse<CreatePostResponse> createPost(CreatePostRequest request) {
        try {
            CreatePostResponse data = postService.createPost(request);
            return ApiResponse.success(data, "게시글 등록 완료");
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    // GET /posts 📝 과제
    public ApiResponse<List<PostResponse>> getAllPosts() {
        List<PostResponse> data = postService.getAllPosts();
        return ApiResponse.success(data, "게시글 목록 조회 성공");
    }

    // GET /posts/{id} 📝 과제
    public ApiResponse<PostResponse> getPost(Long id) {
        try {
            PostResponse data = postService.getPost(id);
            return ApiResponse.success(data, "게시글 조회 성공");
        } catch (PostNotFoundException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    // PUT /posts/{id} 📝 과제
    public ApiResponse<Void> updatePost(Long id, String newTitle, String newContent) {
        try {
            postService.updatePost(id, newTitle, newContent);
            return ApiResponse.success(null, "게시글 수정 완료");
        } catch (PostNotFoundException | IllegalArgumentException e) {
            return ApiResponse.error(e.getMessage());
        }
    }


    // DELETE /posts/{id} 📝 과제
    public ApiResponse<Void> deletePost(Long id) {
        try {
            postService.deletePost(id);
            return ApiResponse.success(null, "게시글 삭제 완료");
        } catch (PostNotFoundException e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}

