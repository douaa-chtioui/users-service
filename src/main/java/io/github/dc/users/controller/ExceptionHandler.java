package io.github.dc.users.controller;

import io.github.dc.users.service.UserAlreadyExistsException;
import io.github.dc.users.service.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({ValidationException.class, UserAlreadyExistsException.class})
    public final ResponseEntity<Object> handleRuntimeException(Exception exception, WebRequest request) {
        HttpStatus status = exception instanceof ValidationException ? HttpStatus.BAD_REQUEST : HttpStatus.CONFLICT;
        return new ResponseEntity<>(exception.getMessage(), status);
    }

}