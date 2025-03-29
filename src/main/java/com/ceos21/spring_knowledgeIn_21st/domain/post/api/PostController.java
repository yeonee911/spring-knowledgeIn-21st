package com.ceos21.spring_knowledgeIn_21st.domain.post.api;

import com.ceos21.spring_knowledgeIn_21st.domain.post.application.PostService;
import com.ceos21.spring_knowledgeIn_21st.domain.post.domain.Post;
import com.ceos21.spring_knowledgeIn_21st.domain.post.dto.request.PostAddRequest;
import com.ceos21.spring_knowledgeIn_21st.domain.post.dto.response.PostResponse;
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
        List<PostResponse> responseDTOs = posts.stream()
                .map(PostResponse::new) // 각 Post를 PostAddResponse로 변환
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        Post post = postService.findPostById(id);
        PostResponse responseDTO = new PostResponse(post);
        return ResponseEntity.ok(responseDTO);
    }
}
