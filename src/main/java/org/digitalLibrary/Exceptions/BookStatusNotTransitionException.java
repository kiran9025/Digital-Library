package org.digitalLibrary.Exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j

public class BookStatusNotTransitionException extends RuntimeException{
    public BookStatusNotTransitionException(String message){
        super(message);
        log.error("Cannot Change the status ");
    }

}
