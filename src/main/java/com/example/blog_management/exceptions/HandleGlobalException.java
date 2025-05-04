package com.example.blog_management.exceptions;

import com.example.blog_management.dtos.responses.RestResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class HandleGlobalException{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<?>> handleMethodArgumentNotValidException
    (MethodArgumentNotValidException ex){
        RestResponse response = new RestResponse();

        response.setMessage("400 Bad request!");
        response.setMessageError(ex.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<RestResponse<?>> handleNoResourceFoundException
    (NoResourceFoundException ex){
        RestResponse response = new RestResponse();

        response.setMessage("404 Not Found!");
        response.setMessageError(ex.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RestResponse<?>> handleEntityNotFoundExceptionException
    (EntityNotFoundException ex){
        RestResponse response = new RestResponse();

        response.setMessage("400 Bad request!");
        response.setMessageError(ex.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.ok(response);
    }

}

