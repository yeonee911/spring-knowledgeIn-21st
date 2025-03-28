package com.ceos21.spring_knowledgeIn_21st.domain.post.application;

import com.ceos21.spring_knowledgeIn_21st.domain.comment.dao.CommentRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.comment.domain.Comment;
import com.ceos21.spring_knowledgeIn_21st.domain.post.dao.PostRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    // 게시글 추가
    @Transactional
    public void savePost(Post post) {
        postRepository.save(post);
    }

    // 게시글 전체 조회
    public List<Post> findPosts() {
        return postRepository.findAll();
    }

    // (특정) 게시글 조회
    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow();
    }
}
