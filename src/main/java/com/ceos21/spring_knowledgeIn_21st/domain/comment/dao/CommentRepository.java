package com.ceos21.spring_knowledgeIn_21st.domain.comment.dao;

import com.ceos21.spring_knowledgeIn_21st.domain.comment.domain.BaseComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<BaseComment, Long> {
    List<BaseComment> findByPostId(Long postId);
}