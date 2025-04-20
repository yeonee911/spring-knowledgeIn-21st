package com.ceos21.spring_knowledgeIn_21st.domain.post.api;

import com.ceos21.spring_knowledgeIn_21st.domain.post.application.PostService;
import com.ceos21.spring_knowledgeIn_21st.domain.post.domain.Post;
import com.ceos21.spring_knowledgeIn_21st.domain.post.dto.request.PostAddRequest;
import com.ceos21.spring_knowledgeIn_21st.domain.post.dto.request.PostUpdateRequest;
import com.ceos21.spring_knowledgeIn_21st.domain.post.dto.response.PostResponse;
import com.ceos21.spring_knowledgeIn_21st.global.common.ApiResponse;
import com.ceos21.spring_knowledgeIn_21st.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    @SecurityRequirement(name = "Authorization")
    @Operation(
            summary = "게시글 추가",
            description = "새로운 게시글을 등록합니다"
    )
    public ResponseEntity<ApiResponse<PostResponse>> savePost(
            @RequestBody PostAddRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        Post savedPost = postService.savePost(request, userDetailsImpl.getUserId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(PostResponse.from(savedPost)));
    }

    /*
    @Operation(summary = "게시글 수정", description = "기존 게시글을 수정합니다")
    @PatchMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest request) {
        postService.updatePost(postId, request);
        PostResponse response = new PostResponse(postId, "게시글이 수정되었습니다.", true);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "게시글 전체 조회", description = "등록된 전체 게시글을 조회합니다")
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<Post> posts = postService.findPosts(); // 모든 게시글 가져오기
        List<PostResponse> response = posts.stream()
                .map(PostResponse::new) // 각 Post를 PostAddResponse로 변환
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "(특정) 게시글 조회", description = "등록된 하나의 게시글을 조회합니다")
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        Post post = postService.findPostById(postId);
        PostResponse response = new PostResponse(post);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "(특정) 게시글 삭제", description = "등록된 하나의 게시글을 삭제합니다")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> deletePost(@PathVariable Long postId, @RequestBody PostAddRequest request) {   // Spring Security의 @AuthenticationPrincipal 사용 예정
        postService.deletePost(postId, request.userId());
        PostResponse response = new PostResponse(postId, "게시글이 삭제되었습니다.", true);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    */
}
