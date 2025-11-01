package com.docsarea.exception;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException (String message){
        super(message) ;
    }
}
