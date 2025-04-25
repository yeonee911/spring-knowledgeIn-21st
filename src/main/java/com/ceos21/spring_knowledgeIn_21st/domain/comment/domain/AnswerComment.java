package com.ceos21.spring_knowledgeIn_21st.domain.comment.domain;

import com.ceos21.spring_knowledgeIn_21st.domain.answer.domain.Answer;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public abstract class AnswerComment extends BaseComment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;
}