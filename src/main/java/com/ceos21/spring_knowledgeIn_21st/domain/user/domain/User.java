package com.ceos21.spring_knowledgeIn_21st.domain.user.domain;

import com.ceos21.spring_knowledgeIn_21st.domain.commentDislike.domain.CommentDislike;
import com.ceos21.spring_knowledgeIn_21st.domain.commentLike.domain.CommentLike;
import com.ceos21.spring_knowledgeIn_21st.domain.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Column(name = "user_email")
    private String email;

    @NotNull
    @Column(name = "user_password")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CommentDislike> commentDislikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CommentLike> commentLikes = new ArrayList<>();

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.posts = new ArrayList<>();
        this.commentDislikes = new ArrayList<>();
        this.commentLikes = new ArrayList<>();
    }
}
