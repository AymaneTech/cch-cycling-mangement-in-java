package com.wora.rider.domain.valueObject;

import jakarta.persistence.Embeddable;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Embeddable
public record RiderId(@UuidGenerator UUID value) {
    public RiderId() {
        this(UUID.randomUUID());
    }
}
