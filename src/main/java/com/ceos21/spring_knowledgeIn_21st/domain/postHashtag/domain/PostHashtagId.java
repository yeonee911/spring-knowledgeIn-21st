package com.ceos21.spring_knowledgeIn_21st.domain.postHashtag.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class PostHashtagId implements Serializable {

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "hashtag_id")
    private Long hashtagId;

    public PostHashtagId(Long postId, Long hashtagId) {
        this.postId = postId;
        this.hashtagId = hashtagId;
    }
}
