package com.biblioteca.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LendBookRequest(@NotNull Long bookId, @NotBlank String memberName) { }
