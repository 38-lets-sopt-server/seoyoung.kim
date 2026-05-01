package org.sopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.sopt.dto.request.LikeRequest;
import org.sopt.dto.response.BaseResponse;
import org.sopt.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Like", description = "좋아요 관련 API")
@RestController
@RequestMapping("/api/v1/posts/{postId}/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @Operation(summary = "좋아요 추가", description = "게시글에 좋아요를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "좋아요 추가 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 또는 유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "409", description = "이미 좋아요를 누른 게시글입니다.")
    })
    @PostMapping
    public ResponseEntity<BaseResponse<Void>> addLike(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId,
            @Valid @RequestBody LikeRequest request
    ) {
        likeService.addLike(postId, request.userId());
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success("좋아요 추가 성공"));
    }

    @Operation(summary = "좋아요 취소", description = "게시글에 누른 좋아요를 취소합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 취소 성공"),
            @ApiResponse(responseCode = "404", description = "좋아요를 누르지 않은 게시글입니다.")
    })
    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> cancelLike(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId,
            @Valid @RequestBody LikeRequest request
    ) {
        likeService.cancelLike(postId, request.userId());
        return ResponseEntity.ok(BaseResponse.success("좋아요 취소 성공"));
    }
}
