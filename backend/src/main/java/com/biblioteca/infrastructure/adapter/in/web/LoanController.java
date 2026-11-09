package com.biblioteca.infrastructure.adapter.in.web;

import com.biblioteca.domain.port.in.LendBookUseCase;
import com.biblioteca.domain.port.in.LendBookUseCase.LendBookCommand;
import com.biblioteca.domain.port.in.ListLoansUseCase;
import com.biblioteca.domain.port.in.ReturnBookUseCase;
import com.biblioteca.infrastructure.adapter.in.web.dto.LendBookRequest;
import com.biblioteca.infrastructure.adapter.in.web.dto.LoanResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    private final LendBookUseCase lendBook;
    private final ReturnBookUseCase returnBook;
    private final ListLoansUseCase listLoans;

    public LoanController(LendBookUseCase lendBook, ReturnBookUseCase returnBook, ListLoansUseCase listLoans) {
        this.lendBook = lendBook;
        this.returnBook = returnBook;
        this.listLoans = listLoans;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponse lend(@Valid @RequestBody LendBookRequest request) {
        var command = new LendBookCommand(request.bookId(), request.memberName());
        return LoanResponse.from(lendBook.lend(command));
    }

    @PutMapping("/{id}/return")
    public LoanResponse giveBack(@PathVariable Long id) {
        return LoanResponse.from(returnBook.returnBook(id));
    }

    @GetMapping
    public List<LoanResponse> list() {
        return listLoans.listAll().stream().map(LoanResponse::from).toList();
    }
}
