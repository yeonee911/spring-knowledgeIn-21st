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
    private final CommentRepository commentRepository;

    // 게시글 저장
    @Transactional
    public void savePost(Post post) {
        postRepository.save(post);
    }

    // 게시글 조회
    public Post findPostById(Long postId) {
        return postRepository.findById(postId);
    }

    // 특정 게시글의 모든 댓글 가져오기
    public List<Comment> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId);
        if (post != null) {
            return post.getComments(); // Post 엔티티에서 comments 리스트 반환
        }
        throw new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다.");
    }
}
