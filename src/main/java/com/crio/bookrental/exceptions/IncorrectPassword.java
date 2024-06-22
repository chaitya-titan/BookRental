package com.crio.bookrental.exceptions;

public class IncorrectPassword extends RuntimeException {
    public IncorrectPassword(String message) {
        super(message);
    }
}
