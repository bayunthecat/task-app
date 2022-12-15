package com.mine.app.exception;

public class EntityNotFoundException extends RuntimeException {

    private Object id;

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(Object id, String message) {
        this.id = id;
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
