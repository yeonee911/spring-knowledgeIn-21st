package com.ceos21.spring_knowledgeIn_21st.global.common;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    @Column(name ="created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name ="updated_at", updatable = false)
    private LocalDateTime updatedAt;

}
