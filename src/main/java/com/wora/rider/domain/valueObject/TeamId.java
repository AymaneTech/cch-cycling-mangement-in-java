package com.wora.rider.domain.valueObject;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record TeamId(UUID value) {
    public TeamId() {
        this(UUID.randomUUID());
    }
}
