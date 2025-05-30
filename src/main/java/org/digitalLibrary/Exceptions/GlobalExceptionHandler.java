package org.digitalLibrary.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourcenotFoundException.class)
    public ResponseEntity<String> handleResourcenotfound(ResourcenotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(MembershipAlreadyExistsExcepton.class)
    public ResponseEntity<String> handlemembershipalreadyexists(MembershipAlreadyExistsExcepton e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(MembershipException.class)
    public ResponseEntity<String> handlemembershipException(MembershipException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(BookStatusNotTransitionException.class)
    public ResponseEntity<String> handleBookStatusNotTransitionException(BookStatusNotTransitionException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
