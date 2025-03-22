package com.ceos21.spring_knowledgeIn_21st.domain.comment.dao;

import com.ceos21.spring_knowledgeIn_21st.domain.comment.domain.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
    }


    public Comment findById(Long id) {
        return em.find(Comment.class, id);
    }

    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    }

    public List<Comment> findByPostId(Long postId) {
        return em.createQuery("SELECT c FROM Comment c WHERE c.post.id = :postId", Comment.class)
                .setParameter("postId", postId)
                .getResultList();
    }
}
