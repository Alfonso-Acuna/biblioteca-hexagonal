package com.biblioteca.application.service;

import com.biblioteca.domain.exception.BookNotFoundException;
import com.biblioteca.domain.exception.LoanNotFoundException;
import com.biblioteca.domain.model.Book;
import com.biblioteca.domain.model.Loan;
import com.biblioteca.domain.port.in.LendBookUseCase;
import com.biblioteca.domain.port.in.ListLoansUseCase;
import com.biblioteca.domain.port.in.ReturnBookUseCase;
import com.biblioteca.domain.port.out.BookRepositoryPort;
import com.biblioteca.domain.port.out.ClockPort;
import com.biblioteca.domain.port.out.LoanRepositoryPort;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class LoanService implements LendBookUseCase, ReturnBookUseCase, ListLoansUseCase {
    private final BookRepositoryPort books;
    private final LoanRepositoryPort loans;
    private final ClockPort clock;

    public LoanService(BookRepositoryPort books, LoanRepositoryPort loans, ClockPort clock) {
        this.books = books;
        this.loans = loans;
        this.clock = clock;
    }

    @Override
    @Transactional
    public Loan lend(LendBookCommand command) {
        Book book = findBook(command.bookId());
        book.lend();
        books.save(book);
        return loans.save(Loan.start(book.getId(), command.memberName(), clock.today()));
    }

    @Override
    @Transactional
    public Loan returnBook(Long loanId) {
        Loan loan = loans.findById(loanId).orElseThrow(() -> new LoanNotFoundException(loanId));
        loan.markReturned(clock.today());
        Book book = findBook(loan.getBookId());
        book.giveBack();
        books.save(book);
        return loans.save(loan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> listAll() {
        return loans.findAll();
    }

    private Book findBook(Long bookId) {
        return books.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
    }
}
