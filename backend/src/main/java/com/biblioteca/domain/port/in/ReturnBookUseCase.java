package com.biblioteca.domain.port.in;

import com.biblioteca.domain.model.Loan;

public interface ReturnBookUseCase {
    Loan returnBook(Long loanId);
}
