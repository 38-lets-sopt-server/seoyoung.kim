package org.sopt.domain.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.sopt.domain.post.dto.CreatePostRequest;
import org.sopt.domain.post.dto.UpdatePostRequest;
import org.sopt.global.dto.BaseResponse;
import org.sopt.domain.post.dto.CreatePostResponse;
import org.sopt.domain.post.dto.PostResponse;
import org.sopt.domain.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post", description = "게시글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    // POST /posts
    @Operation(summary = "게시글 작성", description = "새로운 게시글을 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 작성 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패 (제목/내용 누락 또는 글자 수 초과)"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저")
    })
    @PostMapping
    public ResponseEntity<BaseResponse<CreatePostResponse>> createPost(
            @Valid @RequestBody CreatePostRequest request,
            Authentication authentication
    ) {
        Long userId = Long.parseLong(authentication.getName());
        CreatePostResponse response = postService.createPost(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response, "게시글 등록 성공"));
    }

    // GET /posts
    @Operation(summary = "게시글 목록 조회", description = "전체 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공")
    })
    @GetMapping
    public ResponseEntity<BaseResponse<List<PostResponse>>> getAllPosts() {
        List<PostResponse> responses = postService.getAllPosts();
        return ResponseEntity.ok(BaseResponse.success(responses, "게시글 목록 조회 성공"));

    }

    @Operation(summary = "게시글 단건 조회", description = "게시글 ID로 특정 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    // GET /posts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> getPost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long id
    ) {
        PostResponse response = postService.getPost(id);
        return ResponseEntity.ok(BaseResponse.success(response, "게시글 조회 성공"));
    }

    // PUT /posts/{id}
    @Operation(summary = "게시글 수정", description = "게시글의 제목과 내용을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패"),
            @ApiResponse(responseCode = "403", description = "수정 권한 없음"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> updatePost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdatePostRequest request,
            Authentication authentication
    ) {
        Long userId = Long.parseLong(authentication.getName());
        postService.updatePost(id, request.title(), request.content(), userId);
        return ResponseEntity.ok(BaseResponse.success("게시글 수정 성공"));
    }

    // DELETE /posts/{id}
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.(soft delete)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "403", description = "삭제 권한 없음"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deletePost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long id,
            Authentication authentication
    ) {
        Long userId = Long.parseLong(authentication.getName());
        postService.deletePost(id, userId);
        return ResponseEntity.ok(BaseResponse.success("게시글 삭제 성공"));
    }
}