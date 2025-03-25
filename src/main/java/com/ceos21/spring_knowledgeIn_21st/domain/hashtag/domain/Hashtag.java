package com.ceos21.spring_knowledgeIn_21st.domain.hashtag.domain;

import com.ceos21.spring_knowledgeIn_21st.domain.postHashtag.domain.PostHashtag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Hashtag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    @NotNull
    private String content;

    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL)
    private List<PostHashtag> postHashtags = new ArrayList<>();
}
