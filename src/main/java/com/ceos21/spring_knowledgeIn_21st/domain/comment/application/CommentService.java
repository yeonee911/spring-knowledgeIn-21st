package com.ceos21.spring_knowledgeIn_21st.domain.comment.application;

import com.ceos21.spring_knowledgeIn_21st.domain.comment.dao.CommentRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.comment.domain.BaseComment;
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
    private final CommentRepository commentRepository;

    public CommentService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * 게시글의 댓글 전체 조회
     * @param postId
     * @return
     */
    public List<BaseComment> findAllComments(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }
        return commentRepository.findByPostId(postId);
    }
}
