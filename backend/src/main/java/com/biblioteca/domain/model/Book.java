package com.biblioteca.domain.model;

import com.biblioteca.domain.exception.BookNotAvailableException;

public class Book {
    private final Long id;
    private final String title;
    private final String author;
    private final String isbn;
    private boolean available;

    private Book(Long id, String title, String author, String isbn, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = available;
    }

    /** Crea un libro nuevo (aún sin id) y disponible. */
    public static Book register(String title, String author, String isbn) {
        return new Book(null, requireText(title, "título"), requireText(author, "autor"),
                requireText(isbn, "ISBN"), true);
    }

    /** Reconstruye un libro ya persistido. */
    public static Book restore(Long id, String title, String author, String isbn, boolean available) {
        return new Book(id, title, author, isbn, available);
    }

    public void lend() {
        if (!available) {
            throw new BookNotAvailableException(id);
        }
        available = false;
    }

    public void giveBack() {
        available = true;
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El " + field + " es obligatorio");
        }
        return value.trim();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public boolean isAvailable() { return available; }
}
