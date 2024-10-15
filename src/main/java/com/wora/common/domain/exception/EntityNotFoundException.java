package com.wora.common.domain.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private final Object id;

    public EntityNotFoundException(Object id) {
        super("------------------  Team with id " + id + " not found");
        this.id = id;
    }
}
