package io.github.dc.users.service;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}

