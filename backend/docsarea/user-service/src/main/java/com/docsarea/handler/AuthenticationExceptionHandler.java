package com.docsarea.handler;

import com.docsarea.exception.EmailAlreadyExistException;
import com.docsarea.exception.RegistrationFailedException;
import com.docsarea.exception.UsernameAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthenticationExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistException.class)
    public ResponseEntity<String> usernameAlreadyExistHandler (UsernameAlreadyExistException ex){
        return new ResponseEntity<>(ex.getMessage() , HttpStatus.CONFLICT) ;
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<String> emailAlreadyExistHandler (EmailAlreadyExistException ex ){
        return new ResponseEntity<>(ex.getMessage() , HttpStatus.CONFLICT) ;
    }
    @ExceptionHandler(RegistrationFailedException.class)
    public ResponseEntity<String> registrationFailedHandler (RegistrationFailedException ex ) {
        return new ResponseEntity<>(ex.getMessage() , HttpStatus.CONFLICT) ;
    }
}
