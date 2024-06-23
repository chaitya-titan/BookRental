package com.crio.bookrental.GlobalExceptionHandler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.crio.bookrental.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.status(404).body(exception.getMessage());
    }

    @ExceptionHandler(value = IncorrectPassword.class)
    public ResponseEntity<String> handleIncorrectPasswordException(IncorrectPassword exception) {
        return ResponseEntity.status(400).body(exception.getMessage());
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        return ResponseEntity.status(409).body(exception.getMessage());
    }

    @ExceptionHandler(value = TokenExpiredException.class)
    public ResponseEntity<String> handleTokenExpiredException(TokenExpiredException exception) {
        return ResponseEntity.status(401).body(exception.getMessage());
    }

    @ExceptionHandler(value = UnauthorizedAccessException.class)
    public ResponseEntity<String> handleUnauthorizedAccessException(UnauthorizedAccessException exception) {
        return ResponseEntity.status(403).body(exception.getMessage());
    }

    @ExceptionHandler(value = BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException exception) {
        return ResponseEntity.status(404).body(exception.getMessage());
    }

    @ExceptionHandler(value = MaximumBooksRentedException.class)
    public ResponseEntity<String> handleMaximumBooksRentedException(MaximumBooksRentedException exception) {
        return ResponseEntity.status(400).body(exception.getMessage());
    }

    @ExceptionHandler(value = WrongUserException.class)
    public ResponseEntity<String> handleWrongUserException(WrongUserException exception) {
        return ResponseEntity.status(400).body(exception.getMessage());
    }
}
