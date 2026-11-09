package com.biblioteca.domain.exception;

public class DuplicateIsbnException extends BusinessRuleException {
    public DuplicateIsbnException(String isbn) {
        super("Ya existe un libro con ISBN " + isbn);
    }
}
