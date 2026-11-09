package com.biblioteca.domain.exception;

public class BookNotAvailableException extends BusinessRuleException {
    public BookNotAvailableException(Long id) {
        super("El libro " + id + " ya está prestado");
    }
}
