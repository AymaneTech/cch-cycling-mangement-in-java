package com.wora.comptetition.domain.valueObject;

import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.UUID;

public record StageId(@UuidGenerator UUID value) implements Serializable {
    public StageId() {
        this(UUID.randomUUID());
    }
}
