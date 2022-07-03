package io.github.dc.users.service;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("A user with the same name and birthdate already exists");
    }
}
