package com.interview.handler;

import com.interview.exception.GithubException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GeneralExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> generalErrorResponse(RuntimeException e) {
        return ResponseEntity.internalServerError().body("Some unexpected error occurred. Please try again later.");
    }

    @ExceptionHandler(FeignException.NotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> generalErrorResponse(FeignException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body("Incorrect login.");
    }

    @ExceptionHandler(GithubException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> githubExceptionResponse(GithubException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

}
