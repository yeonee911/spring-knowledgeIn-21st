package com.ceos21.spring_knowledgeIn_21st.domain.comment.application;

import com.ceos21.spring_knowledgeIn_21st.domain.comment.dao.PostCommentRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.comment.domain.PostComment;
import com.ceos21.spring_knowledgeIn_21st.domain.post.dao.PostRepository;
import com.ceos21.spring_knowledgeIn_21st.global.exception.CustomException;
import com.ceos21.spring_knowledgeIn_21st.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentService {
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;

    public CommentService(PostRepository postRepository, PostCommentRepository postCommentRepository) {
        this.postRepository = postRepository;
        this.postCommentRepository = postCommentRepository;
    }

    /**
     * 게시글의 댓글 전체 조회
     * @param postId
     * @return
     */
    public List<PostComment> findAllComments(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }
        return postCommentRepository.findByPostId(postId);
    }
}
