package com.ceos21.spring_knowledgeIn_21st.service;

import com.ceos21.spring_knowledgeIn_21st.domain.comment.application.CommentService;
import com.ceos21.spring_knowledgeIn_21st.domain.comment.domain.Comment;
import com.ceos21.spring_knowledgeIn_21st.domain.post.dao.PostRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.post.domain.Post;
import com.ceos21.spring_knowledgeIn_21st.domain.user.dao.UserRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;

    private User user;
    private Post post;

    @BeforeEach
    void setUp() {
        // User 저장
        user = new User("testUser", "test@example.com", "password");
        userRepository.save(user);  // User 저장

        // Post 저장
        post = new Post(user, "테스트 제목", "테스트 내용");
        postRepository.save(post);
    }

    @Test
    void 게시글에_댓글_3개를_저장하고_조회한다() {
        // given
        Comment comment1 = new Comment(user, post, "댓글1");
        Comment comment2 = new Comment(user, post, "댓글2");
        Comment comment3 = new Comment(user, post, "댓글3");

        commentService.saveComment(post.getId(), comment1);
        commentService.saveComment(post.getId(), comment2);
        commentService.saveComment(post.getId(), comment3);

        // when
        List<Comment> comments = commentService.findCommentsByPostId(post.getId());

        // then
        assertThat(comments).hasSize(3);
        assertThat(comments).extracting("content").containsExactly("댓글1", "댓글2", "댓글3");
        assertThat(comments).allMatch(comment -> comment.getPost().equals(post));
        assertThat(comments).allMatch(comment -> comment.getUser().equals(user));
    }

}
