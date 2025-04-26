package com.ceos21.spring_knowledgeIn_21st.domain.comment.application;

import com.ceos21.spring_knowledgeIn_21st.domain.answer.dao.AnswerRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.answer.domain.Answer;
import com.ceos21.spring_knowledgeIn_21st.domain.comment.dao.AnswerCommentRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.comment.domain.AnswerComment;
import com.ceos21.spring_knowledgeIn_21st.domain.comment.dto.request.CommentAddRequest;
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
    private final UserRepository userRepository;

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

    /**
     * 답변 댓글 추가
     * @param request
     * @return
     */
    public AnswerComment addComment(CommentAddRequest request, Long answerId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(()->new CustomException(ErrorCode.ANSWER_NOT_FOUND));
        AnswerComment comment = request.toAnswerCommentEntity(answer, user);
        answer.addAnswerComment(comment);
        return answerCommentRepository.save(comment);
    }
}
