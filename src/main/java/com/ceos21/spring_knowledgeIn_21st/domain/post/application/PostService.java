package com.ceos21.spring_knowledgeIn_21st.domain.post.application;

import com.ceos21.spring_knowledgeIn_21st.domain.image.application.ImageService;
import com.ceos21.spring_knowledgeIn_21st.domain.post.dao.PostRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.post.domain.Post;
import com.ceos21.spring_knowledgeIn_21st.domain.post.dto.request.PostAddRequest;
import com.ceos21.spring_knowledgeIn_21st.domain.postHashtag.application.PostHashtagService;
import com.ceos21.spring_knowledgeIn_21st.domain.user.dao.UserRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.user.domain.User;
import com.ceos21.spring_knowledgeIn_21st.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ceos21.spring_knowledgeIn_21st.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostHashtagService postHashtagService;
    private final ImageService imageService;

    /**
     * 게시글 추가
     * */
    @Transactional
    public Post savePost(PostAddRequest request) {
        // 작성자 조회
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Post savedPost = postRepository.save(request.toEntity(user));

        // 이미지 저장
        imageService.saveImageUrls(savedPost, request.imageUrls());

        // 해쉬 태그 저장
        postHashtagService.saveHashtag(savedPost, request.hashtags());

        return savedPost;
    }

    /**
     * 게시글 전체 조회
     * */
    public List<Post> findPosts() {
        return postRepository.findAll();
    }

    /**
     * (특정) 게시글 조회
     * */
    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(()-> new CustomException(POST_NOT_FOUND));
    }

    /**
     * (특정) 게시글 수정
     * */
    public void updatePost(Post post) {

    }

    /**
     * (특정) 게시글 삭제
     * */
    @Transactional
    public void deletePost(Long postId, Long userId) {
        // 게시글 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomException(POST_NOT_FOUND));

        // 게시글 작성자와 삭제 요청자 일치 확인
        if (!post.getUser().getId().equals(userId)) {
            throw new CustomException(POST_ACCESS_DENIED);
        }

        postRepository.delete(post);
    }



    /**
     * (특정) 해시태그를 통한 게시글 조회
     * */
}
