package com.ceos21.spring_knowledgeIn_21st.domain.image.dao;

import com.ceos21.spring_knowledgeIn_21st.domain.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
