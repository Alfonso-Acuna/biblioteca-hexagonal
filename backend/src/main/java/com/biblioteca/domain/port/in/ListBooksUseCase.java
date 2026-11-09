package com.biblioteca.domain.port.in;

import com.biblioteca.domain.model.Book;
import java.util.List;

public interface ListBooksUseCase {
    List<Book> listAll();
}
