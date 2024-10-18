package com.wora.rider.domain.valueObject;

import jakarta.persistence.Embeddable;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Embeddable
public record TeamId(@UuidGenerator UUID value) {
    public TeamId() {
        this(UUID.randomUUID());
    }
}
