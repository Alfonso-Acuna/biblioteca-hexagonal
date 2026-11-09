package com.biblioteca.domain.port.in;

import com.biblioteca.domain.model.Book;

public interface RegisterBookUseCase {
    Book register(RegisterBookCommand command);

    record RegisterBookCommand(String title, String author, String isbn) { }
}
