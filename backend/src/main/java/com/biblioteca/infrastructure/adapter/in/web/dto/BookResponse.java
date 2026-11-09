package com.biblioteca.infrastructure.adapter.in.web.dto;

import com.biblioteca.domain.model.Book;

public record BookResponse(Long id, String title, String author, String isbn, boolean available) {
    public static BookResponse from(Book b) {
        return new BookResponse(b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.isAvailable());
    }
}
