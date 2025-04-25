package com.ceos21.spring_knowledgeIn_21st.domain.comment.dao;

import com.ceos21.spring_knowledgeIn_21st.domain.comment.domain.AnswerComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerCommentRepository extends JpaRepository<AnswerComment, Long> {
    List<AnswerComment> findByAnswerId(Long answerId);
}
