package com.ceos21.spring_knowledgeIn_21st.domain.post.application;

import com.ceos21.spring_knowledgeIn_21st.domain.hashtag.dao.HashtagRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.hashtag.domain.Hashtag;
import com.ceos21.spring_knowledgeIn_21st.domain.image.application.ImageService;
import com.ceos21.spring_knowledgeIn_21st.domain.image.dao.ImageRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.post.dao.PostRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.post.domain.Post;
import com.ceos21.spring_knowledgeIn_21st.domain.post.dto.request.PostAddRequest;
import com.ceos21.spring_knowledgeIn_21st.domain.post.dto.request.PostUpdateRequest;
import com.ceos21.spring_knowledgeIn_21st.domain.postHashtag.application.PostHashtagService;
import com.ceos21.spring_knowledgeIn_21st.domain.postHashtag.dao.PostHashtagRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.postHashtag.domain.PostHashtag;
import com.ceos21.spring_knowledgeIn_21st.domain.user.dao.UserRepository;
import com.ceos21.spring_knowledgeIn_21st.domain.user.domain.User;
import com.ceos21.spring_knowledgeIn_21st.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.ceos21.spring_knowledgeIn_21st.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostHashtagService postHashtagService;
    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final PostHashtagRepository postHashtagRepository;
    private final HashtagRepository hashtagRepository;

    /**
     * 게시글 추가
     * */
    @Transactional
    public Post savePost(PostAddRequest request, Long userId) {
        // 작성자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        // 게시글 저장
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
    @Transactional
    public void updatePost(Long postId, PostUpdateRequest request) {
        // 게시글 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomException(POST_NOT_FOUND));

        // 게시글 작성자와 수정 요청자 일치 확인
        if (!post.getUser().getId().equals(request.userId())) {
            throw new CustomException(POST_ACCESS_DENIED);
        }

        // 제목, 내용 수정
        post.update(request.title(), request.content());

        //기존 이미지 삭제 및 새 이미지 저장
        imageRepository.deleteByPost(post);
        imageService.saveImageUrls(post, request.imageUrls());

        // 새 해시태그 저장
        postHashtagService.saveHashtag(post, request.hashtags());
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

        List<PostHashtag> postHashtags = post.getPostHashtags();
        for(PostHashtag postHashtag : postHashtags) {
            Hashtag hashtag = postHashtag.getHashtag();
            hashtag.decreasePostCount();
            if (hashtag.getPostCount() == 0) {
                hashtagRepository.delete(hashtag);
            }
        }

        postRepository.delete(post);
    }



    /**
     * (특정) 해시태그를 통한 게시글 조회
     * */
    public List<Post> findPostByHashtag(String hashtagContent) {
        // 해시태그가 존재하는지 확인
        Hashtag hashtag = hashtagRepository.findByContent(hashtagContent)
                .orElseThrow(() -> new CustomException(HASHTAG_NOT_FOUND));

        System.out.println("🔍 hashtagContent = " + hashtagContent);
        System.out.println("🔍 hashtag.id = " + hashtag.getId());

        List<Post> posts = postHashtagRepository.findByHashtag(hashtag).stream()
                .map(PostHashtag::getPost)
                .collect(Collectors.toList());
        return posts;
    }
}
