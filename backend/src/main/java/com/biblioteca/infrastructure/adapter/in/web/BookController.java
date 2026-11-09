package com.biblioteca.infrastructure.adapter.in.web;

import com.biblioteca.domain.port.in.ListBooksUseCase;
import com.biblioteca.domain.port.in.RegisterBookUseCase;
import com.biblioteca.domain.port.in.RegisterBookUseCase.RegisterBookCommand;
import com.biblioteca.infrastructure.adapter.in.web.dto.BookResponse;
import com.biblioteca.infrastructure.adapter.in.web.dto.CreateBookRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final RegisterBookUseCase registerBook;
    private final ListBooksUseCase listBooks;

    public BookController(RegisterBookUseCase registerBook, ListBooksUseCase listBooks) {
        this.registerBook = registerBook;
        this.listBooks = listBooks;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse register(@Valid @RequestBody CreateBookRequest request) {
        var command = new RegisterBookCommand(request.title(), request.author(), request.isbn());
        return BookResponse.from(registerBook.register(command));
    }

    @GetMapping
    public List<BookResponse> list() {
        return listBooks.listAll().stream().map(BookResponse::from).toList();
    }
}
