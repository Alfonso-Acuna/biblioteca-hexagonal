package com.biblioteca.application;

import static org.junit.jupiter.api.Assertions.*;

import com.biblioteca.application.service.BookService;
import com.biblioteca.application.service.LoanService;
import com.biblioteca.domain.exception.*;
import com.biblioteca.domain.model.Book;
import com.biblioteca.domain.model.Loan;
import com.biblioteca.domain.port.in.LendBookUseCase.LendBookCommand;
import com.biblioteca.domain.port.in.RegisterBookUseCase.RegisterBookCommand;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoanServiceTest {
    private static final LocalDate TODAY = LocalDate.of(2026, 11, 9);

    private InMemoryRepositories.Books books;
    private BookService bookService;
    private LoanService loanService;
    private Book book;

    @BeforeEach
    void setUp() {
        books = new InMemoryRepositories.Books();
        bookService = new BookService(books);
        loanService = new LoanService(books, new InMemoryRepositories.Loans(), () -> TODAY);
        book = bookService.register(new RegisterBookCommand("Clean Code", "Robert C. Martin", "9780132350884"));
    }

    @Test
    void lendingABookMakesItUnavailableAndSetsDueDate() {
        Loan loan = loanService.lend(new LendBookCommand(book.getId(), "Ana"));

        assertFalse(books.findById(book.getId()).orElseThrow().isAvailable());
        assertEquals(TODAY.plusDays(14), loan.getDueDate());
    }

    @Test
    void lendingAnUnavailableBookFails() {
        loanService.lend(new LendBookCommand(book.getId(), "Ana"));

        assertThrows(BookNotAvailableException.class,
                () -> loanService.lend(new LendBookCommand(book.getId(), "Luis")));
    }

    @Test
    void lendingAnUnknownBookFails() {
        assertThrows(BookNotFoundException.class,
                () -> loanService.lend(new LendBookCommand(99L, "Ana")));
    }

    @Test
    void returningABookMakesItAvailableAgain() {
        Loan loan = loanService.lend(new LendBookCommand(book.getId(), "Ana"));

        Loan returned = loanService.returnBook(loan.getId());

        assertTrue(returned.isReturned());
        assertTrue(books.findById(book.getId()).orElseThrow().isAvailable());
    }

    @Test
    void returningTwiceFails() {
        Loan loan = loanService.lend(new LendBookCommand(book.getId(), "Ana"));
        loanService.returnBook(loan.getId());

        assertThrows(LoanAlreadyReturnedException.class, () -> loanService.returnBook(loan.getId()));
    }

    @Test
    void registeringDuplicateIsbnFails() {
        assertThrows(DuplicateIsbnException.class,
                () -> bookService.register(new RegisterBookCommand("Otro", "Autor", "9780132350884")));
    }
}
