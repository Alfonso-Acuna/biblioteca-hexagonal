package com.biblioteca.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateBookRequest(@NotBlank String title, @NotBlank String author, @NotBlank String isbn) { }
