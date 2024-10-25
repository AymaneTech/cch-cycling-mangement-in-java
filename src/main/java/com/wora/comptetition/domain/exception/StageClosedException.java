package com.wora.comptetition.domain.exception;

public class StageClosedException extends RuntimeException {
    public StageClosedException(String message) {
        super(message);
    }
}
