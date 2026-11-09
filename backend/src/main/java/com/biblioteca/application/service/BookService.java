package com.biblioteca.application.service;

import com.biblioteca.domain.exception.DuplicateIsbnException;
import com.biblioteca.domain.model.Book;
import com.biblioteca.domain.port.in.ListBooksUseCase;
import com.biblioteca.domain.port.in.RegisterBookUseCase;
import com.biblioteca.domain.port.out.BookRepositoryPort;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class BookService implements RegisterBookUseCase, ListBooksUseCase {
    private final BookRepositoryPort books;

    public BookService(BookRepositoryPort books) {
        this.books = books;
    }

    @Override
    @Transactional
    public Book register(RegisterBookCommand command) {
        Book book = Book.register(command.title(), command.author(), command.isbn());
        ensureIsbnIsUnique(book.getIsbn());
        return books.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> listAll() {
        return books.findAll();
    }

    private void ensureIsbnIsUnique(String isbn) {
        if (books.existsByIsbn(isbn)) {
            throw new DuplicateIsbnException(isbn);
        }
    }
}
