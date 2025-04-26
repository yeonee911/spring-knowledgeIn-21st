package com.ceos21.spring_knowledgeIn_21st.domain.comment.application;

import com.ceos21.spring_knowledgeIn_21st.domain.comment.dao.PostCommentRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.comment.domain.PostComment;
import com.ceos21.spring_knowledgeIn_21st.domain.comment.dto.request.CommentAddRequest;
import com.ceos21.spring_knowledgeIn_21st.domain.post.dao.PostRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.post.domain.Post;
import com.ceos21.spring_knowledgeIn_21st.domain.user.dao.UserRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.user.domain.User;
import com.ceos21.spring_knowledgeIn_21st.global.exception.CustomException;
import com.ceos21.spring_knowledgeIn_21st.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostCommentService {
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final UserRepository userRepository;

    public PostCommentService(PostRepository postRepository, PostCommentRepository postCommentRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postCommentRepository = postCommentRepository;
        this.userRepository = userRepository;
    }

    /**
     * 게시글의 댓글 전체 조회
     * @param postId
     * @return
     */
    public List<PostComment> findAllComments(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }
        return postCommentRepository.findByPostId(postId);
    }

    /**
     * 게시글 댓글 추가
     * @param request
     * @param userId
     * @return
     */
    public PostComment addComment(CommentAddRequest request, Long postId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new CustomException(ErrorCode.POST_NOT_FOUND));
        PostComment comment = request.toPostCommentEntity(post, user);
        post.addPostComment(comment);
        return postCommentRepository.save(comment);
    }
}
