package com.ceos21.spring_knowledgeIn_21st.domain.comment.application;

import com.ceos21.spring_knowledgeIn_21st.domain.answer.dao.AnswerRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.comment.dao.AnswerCommentRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.comment.domain.AnswerComment;
import com.ceos21.spring_knowledgeIn_21st.domain.user.dao.UserRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.user.domain.User;
import com.ceos21.spring_knowledgeIn_21st.global.exception.CustomException;
import com.ceos21.spring_knowledgeIn_21st.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerCommentService {
    private final AnswerCommentRepository answerCommentRepository;
    private final AnswerRepository answerRepository;

    /**
     * 답변 댓글 전체 조회
     * @param answerId
     * @return
     */
    public List<AnswerComment> findAllComments(Long answerId) {
        if (!answerRepository.existsById(answerId)) {
            throw new CustomException(ErrorCode.ANSWER_NOT_FOUND);
        }
        return answerCommentRepository.findByAnswerId(answerId);
    }
}
