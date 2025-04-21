package com.ceos21.spring_knowledgeIn_21st.domain.answer.application;

import com.ceos21.spring_knowledgeIn_21st.domain.answer.dao.AnswerRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.answer.domain.Answer;
import com.ceos21.spring_knowledgeIn_21st.domain.answer.dto.request.AnswerAddRequest;
import com.ceos21.spring_knowledgeIn_21st.domain.post.dao.PostRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.post.domain.Post;
import com.ceos21.spring_knowledgeIn_21st.domain.user.dao.UserRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.user.domain.User;
import com.ceos21.spring_knowledgeIn_21st.global.exception.CustomException;
import com.ceos21.spring_knowledgeIn_21st.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    /**
     * 답변 추가
     */
    @Transactional
    public Answer saveAnswer(AnswerAddRequest request, Long postId, Long userId) {
        // 작성자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        // 질문글 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        // 본인 질문글에는 답변 불가
        if (post.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.CANNOT_ANSWER_OWN_POST);
        }

        Answer savedAnswer = answerRepository.save(request.toEntity(user, post));

        return savedAnswer;
    }
}
