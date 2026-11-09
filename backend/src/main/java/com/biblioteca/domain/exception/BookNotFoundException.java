package com.biblioteca.domain.exception;

public class BookNotFoundException extends ResourceNotFoundException {
    public BookNotFoundException(Long id) {
        super("No existe el libro con id " + id);
    }
}
