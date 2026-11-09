package com.biblioteca.infrastructure.adapter.in.web.dto;

import com.biblioteca.domain.model.Loan;
import java.time.LocalDate;

public record LoanResponse(Long id, Long bookId, String memberName, LocalDate loanDate,
                           LocalDate dueDate, LocalDate returnDate, boolean returned) {
    public static LoanResponse from(Loan l) {
        return new LoanResponse(l.getId(), l.getBookId(), l.getMemberName(), l.getLoanDate(),
                l.getDueDate(), l.getReturnDate(), l.isReturned());
    }
}
