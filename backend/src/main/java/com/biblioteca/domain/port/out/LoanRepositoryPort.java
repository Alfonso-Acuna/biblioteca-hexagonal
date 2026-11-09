package com.biblioteca.domain.port.out;

import com.biblioteca.domain.model.Loan;
import java.util.List;
import java.util.Optional;

public interface LoanRepositoryPort {
    Loan save(Loan loan);
    Optional<Loan> findById(Long id);
    List<Loan> findAll();
}
