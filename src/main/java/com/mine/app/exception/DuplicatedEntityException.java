package com.mine.app.exception;

public class DuplicatedEntityException extends RuntimeException {

    public DuplicatedEntityException() {
    }

    public DuplicatedEntityException(String message) {
        super(message);
    }

    public DuplicatedEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
