package com.ceos21.spring_knowledgeIn_21st.domain.comment.domain;

import com.ceos21.spring_knowledgeIn_21st.domain.answer.domain.Answer;
import com.ceos21.spring_knowledgeIn_21st.domain.baseEntity.domain.BaseEntity;
import com.ceos21.spring_knowledgeIn_21st.domain.post.domain.Post;
import com.ceos21.spring_knowledgeIn_21st.domain.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @NotBlank
    private String content;
}
