package com.wora.comptetition.domain.exception;

public class CompetitionClosedException extends RuntimeException {
    public CompetitionClosedException(String message) {
        super(message);
    }
}
