package com.ceos21.spring_knowledgeIn_21st.domain.postHashtag.domain;

import com.ceos21.spring_knowledgeIn_21st.domain.hashtag.domain.Hashtag;
import com.ceos21.spring_knowledgeIn_21st.domain.post.domain.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostHashtag {

    @EmbeddedId
    private PostHashtagId id;

    @NotNull
    @MapsId("postId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @NotNull
    @MapsId("hashtagId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;
}
