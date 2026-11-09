package com.biblioteca.infrastructure.adapter.out.persistence;

import com.biblioteca.domain.model.Book;
import com.biblioteca.domain.model.Loan;

/** Traduce entre el modelo de dominio y el modelo de persistencia. */
final class PersistenceMapper {
    private PersistenceMapper() { }

    static BookEntity toEntity(Book b) {
        return new BookEntity(b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.isAvailable());
    }

    static Book toDomain(BookEntity e) {
        return Book.restore(e.getId(), e.getTitle(), e.getAuthor(), e.getIsbn(), e.isAvailable());
    }

    static LoanEntity toEntity(Loan l) {
        return new LoanEntity(l.getId(), l.getBookId(), l.getMemberName(),
                l.getLoanDate(), l.getDueDate(), l.getReturnDate());
    }

    static Loan toDomain(LoanEntity e) {
        return Loan.restore(e.getId(), e.getBookId(), e.getMemberName(),
                e.getLoanDate(), e.getDueDate(), e.getReturnDate());
    }
}
