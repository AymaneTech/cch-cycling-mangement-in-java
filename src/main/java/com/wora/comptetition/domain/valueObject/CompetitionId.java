package com.wora.comptetition.domain.valueObject;

import jakarta.persistence.Embeddable;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Embeddable
public record CompetitionId(@UuidGenerator UUID value) {
    public CompetitionId() {
        this(UUID.randomUUID());
    }
}
