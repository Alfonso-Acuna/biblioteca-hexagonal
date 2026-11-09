package com.biblioteca.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBookRepository extends JpaRepository<BookEntity, Long> {
    boolean existsByIsbn(String isbn);
}
