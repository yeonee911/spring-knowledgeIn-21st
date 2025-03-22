package com.ceos21.spring_knowledgeIn_21st.domain.post.dao;

import com.ceos21.spring_knowledgeIn_21st.domain.post.domain.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public Post findById(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public List<Post> findByTitle(String title) {
        return em.createQuery("select p from Post p where m.title = :title", Post.class)
                .setParameter("title", title)
                .getResultList();
    }
}
