package com.ceos21.spring_knowledgeIn_21st.domain.comment.dao;

import com.ceos21.spring_knowledgeIn_21st.domain.comment.domain.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    List<PostComment> findByPostId(Long postId);
}