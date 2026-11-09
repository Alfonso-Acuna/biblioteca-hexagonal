package com.biblioteca.application;

import com.biblioteca.domain.model.Book;
import com.biblioteca.domain.model.Loan;
import com.biblioteca.domain.port.out.BookRepositoryPort;
import com.biblioteca.domain.port.out.LoanRepositoryPort;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/** Adaptadores falsos: prueban que el núcleo funciona sin Spring ni base de datos. */
final class InMemoryRepositories {
    private InMemoryRepositories() { }

    static class Books implements BookRepositoryPort {
        private final Map<Long, Book> store = new LinkedHashMap<>();
        private final AtomicLong ids = new AtomicLong();

        @Override
        public Book save(Book b) {
            Long id = b.getId() != null ? b.getId() : ids.incrementAndGet();
            Book saved = Book.restore(id, b.getTitle(), b.getAuthor(), b.getIsbn(), b.isAvailable());
            store.put(id, saved);
            return saved;
        }

        @Override public Optional<Book> findById(Long id) { return Optional.ofNullable(store.get(id)); }
        @Override public List<Book> findAll() { return new ArrayList<>(store.values()); }
        @Override public boolean existsByIsbn(String isbn) {
            return store.values().stream().anyMatch(b -> b.getIsbn().equals(isbn));
        }
    }

    static class Loans implements LoanRepositoryPort {
        private final Map<Long, Loan> store = new LinkedHashMap<>();
        private final AtomicLong ids = new AtomicLong();

        @Override
        public Loan save(Loan l) {
            Long id = l.getId() != null ? l.getId() : ids.incrementAndGet();
            Loan saved = Loan.restore(id, l.getBookId(), l.getMemberName(),
                    l.getLoanDate(), l.getDueDate(), l.getReturnDate());
            store.put(id, saved);
            return saved;
        }

        @Override public Optional<Loan> findById(Long id) { return Optional.ofNullable(store.get(id)); }
        @Override public List<Loan> findAll() { return new ArrayList<>(store.values()); }
    }
}
