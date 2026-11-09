package com.biblioteca.infrastructure.adapter.out.persistence;

import com.biblioteca.domain.model.Book;
import com.biblioteca.domain.port.out.BookRepositoryPort;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class BookPersistenceAdapter implements BookRepositoryPort {
    private final JpaBookRepository jpa;

    public BookPersistenceAdapter(JpaBookRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Book save(Book book) {
        return PersistenceMapper.toDomain(jpa.save(PersistenceMapper.toEntity(book)));
    }

    @Override
    public Optional<Book> findById(Long id) {
        return jpa.findById(id).map(PersistenceMapper::toDomain);
    }

    @Override
    public List<Book> findAll() {
        return jpa.findAll().stream().map(PersistenceMapper::toDomain).toList();
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return jpa.existsByIsbn(isbn);
    }
}
