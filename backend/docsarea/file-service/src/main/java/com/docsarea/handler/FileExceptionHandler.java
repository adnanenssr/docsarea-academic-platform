package com.docsarea.handler;

import com.docsarea.exception.ForbiddenResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FileExceptionHandler {
    @ExceptionHandler(ForbiddenResourceException.class)
    public ResponseEntity<String> forbidenResourceHandler(ForbiddenResourceException ex){
        return new ResponseEntity<>(ex.getMessage() , HttpStatus.FORBIDDEN) ;
    }

}
