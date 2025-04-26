package com.ceos21.spring_knowledgeIn_21st.domain.comment.application;

import com.ceos21.spring_knowledgeIn_21st.domain.comment.dao.AnswerCommentRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.comment.dao.PostCommentRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.comment.domain.AnswerComment;
import com.ceos21.spring_knowledgeIn_21st.domain.comment.domain.BaseComment;
import com.ceos21.spring_knowledgeIn_21st.domain.comment.domain.PostComment;
import com.ceos21.spring_knowledgeIn_21st.global.exception.CustomException;
import com.ceos21.spring_knowledgeIn_21st.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private AnswerCommentRepository answerCommentRepository;
    private PostCommentRepository postCommentRepository;

    /**
     * 댓글 삭제
     * @param commentId
     * @param userId
     * @return
     */
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        BaseComment comment = findByCommentId(commentId);
        if (!comment.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.COMMENT_ACCESS_DENIED);
        }
        deleteCommentEntity(comment);
    }

    /**
     * 댓글 PostComment, AnswerComment 통합 조회
     * @param commentId
     * @return
     */
    private BaseComment findByCommentId(Long commentId) {
        return postCommentRepository.findById(commentId)
                .map(c -> (BaseComment) c)
                .or(() -> answerCommentRepository.findById(commentId)
                .map(c->(BaseComment) c))
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

    }

    /**
     * 댓글 통합 삭제
     * @param comment
     */
    private void deleteCommentEntity(BaseComment comment) {
        if (comment instanceof PostComment) {
            postCommentRepository.delete((PostComment) comment);
        }else if (comment instanceof AnswerComment) {
            answerCommentRepository.delete((AnswerComment) comment);
        }
        else {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
