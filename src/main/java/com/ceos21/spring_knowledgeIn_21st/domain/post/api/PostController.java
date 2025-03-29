package com.ceos21.spring_knowledgeIn_21st.domain.post.api;

import com.ceos21.spring_knowledgeIn_21st.domain.post.application.PostService;
import com.ceos21.spring_knowledgeIn_21st.domain.post.domain.Post;
import com.ceos21.spring_knowledgeIn_21st.domain.post.dto.request.PostAddRequest;
import com.ceos21.spring_knowledgeIn_21st.domain.post.dto.response.PostDeleteResponse;
import com.ceos21.spring_knowledgeIn_21st.domain.post.dto.response.PostResponse;
import com.ceos21.spring_knowledgeIn_21st.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<Long> savePost(@RequestBody PostAddRequest postAddRequest) {
        Post savedPost = postService.savePost(postAddRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedPost.getId());
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<Post> posts = postService.findPosts(); // 모든 게시글 가져오기
        List<PostResponse> response = posts.stream()
                .map(PostResponse::new) // 각 Post를 PostAddResponse로 변환
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        Post post = postService.findPostById(postId);
        PostResponse response = new PostResponse(post);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<PostDeleteResponse> deletePost(@PathVariable Long postId, @RequestBody PostAddRequest request) {   // Spring Security의 @AuthenticationPrincipal 사용 예정
        postService.deletePost(postId, request.userId());
        PostDeleteResponse response = new PostDeleteResponse(postId, "게시글이 삭제되었습니다.", true);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
