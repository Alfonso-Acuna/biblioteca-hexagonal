package com.biblioteca.domain.model;

import com.biblioteca.domain.exception.LoanAlreadyReturnedException;
import java.time.LocalDate;

public class Loan {
    private static final int LOAN_DAYS = 14;

    private final Long id;
    private final Long bookId;
    private final String memberName;
    private final LocalDate loanDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;

    private Loan(Long id, Long bookId, String memberName, LocalDate loanDate,
                 LocalDate dueDate, LocalDate returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.memberName = memberName;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public static Loan start(Long bookId, String memberName, LocalDate today) {
        if (memberName == null || memberName.isBlank()) {
            throw new IllegalArgumentException("El nombre del socio es obligatorio");
        }
        return new Loan(null, bookId, memberName.trim(), today, today.plusDays(LOAN_DAYS), null);
    }

    public static Loan restore(Long id, Long bookId, String memberName, LocalDate loanDate,
                               LocalDate dueDate, LocalDate returnDate) {
        return new Loan(id, bookId, memberName, loanDate, dueDate, returnDate);
    }

    public void markReturned(LocalDate today) {
        if (isReturned()) {
            throw new LoanAlreadyReturnedException(id);
        }
        returnDate = today;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public Long getId() { return id; }
    public Long getBookId() { return bookId; }
    public String getMemberName() { return memberName; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
}
