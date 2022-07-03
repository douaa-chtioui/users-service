package io.github.dc.users.controller;

import io.github.dc.users.domain.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping)")
    public Object log(ProceedingJoinPoint point) throws Throwable {
        Instant start = Instant.now();
        ResponseEntity<User> response = null;
        try {
            response = (ResponseEntity<User>) point.proceed();
            return response;
        } finally {
            long processingTimeInMillis = Duration.between(start, Instant.now()).toMillis();
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            User output = Optional.ofNullable(response).map(HttpEntity::getBody).orElse(null);
            if (method.isAnnotationPresent(GetMapping.class)) {
                String path = method.getAnnotation(GetMapping.class).value()[0];
                LOGGER.info("Get {}, Output {}, Processing time {} ms", path, output, processingTimeInMillis);
            } else if (method.isAnnotationPresent(PostMapping.class)) {
                String path = method.getAnnotation(PostMapping.class).value()[0];
                Object input = point.getArgs()[0];
                LOGGER.info("Post {}, Input {}, Output {}, Processing time {} ms", path, input, output, processingTimeInMillis);
            }
        }
    }
}
