package com.crio.bookrental.exceptions;

public class MaximumBooksRentedException extends RuntimeException {
    public MaximumBooksRentedException(String message) {
        super(message);
    }
}
