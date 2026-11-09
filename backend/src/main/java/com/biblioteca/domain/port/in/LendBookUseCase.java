package com.biblioteca.domain.port.in;

import com.biblioteca.domain.model.Loan;

public interface LendBookUseCase {
    Loan lend(LendBookCommand command);

    record LendBookCommand(Long bookId, String memberName) { }
}
