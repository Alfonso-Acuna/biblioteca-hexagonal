package com.biblioteca.domain.exception;

public class LoanNotFoundException extends ResourceNotFoundException {
    public LoanNotFoundException(Long id) {
        super("No existe el préstamo con id " + id);
    }
}
