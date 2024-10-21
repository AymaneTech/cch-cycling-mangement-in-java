package com.wora.common.domain.valueObject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Embeddable
public record Timestamp(
        @CreationTimestamp
        @Column(name = "created_at")
        LocalDateTime createdAt,

        @UpdateTimestamp
        @Column(name = "updated_at")
        LocalDateTime updatedAt
) {
}
