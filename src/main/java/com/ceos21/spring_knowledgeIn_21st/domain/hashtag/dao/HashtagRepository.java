package com.ceos21.spring_knowledgeIn_21st.domain.hashtag.dao;

import com.ceos21.spring_knowledgeIn_21st.domain.hashtag.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
}
