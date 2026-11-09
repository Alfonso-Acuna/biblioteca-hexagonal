package com.biblioteca.infrastructure.config;

import com.biblioteca.application.service.BookService;
import com.biblioteca.application.service.LoanService;
import com.biblioteca.domain.port.out.BookRepositoryPort;
import com.biblioteca.domain.port.out.ClockPort;
import com.biblioteca.domain.port.out.LoanRepositoryPort;
import java.time.LocalDate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Composition root: aquí (y solo aquí) se conectan casos de uso con adaptadores. */
@Configuration
public class BeanConfig {

    @Bean
    ClockPort clockPort() {
        return () -> LocalDate.now();
    }

    @Bean
    BookService bookService(BookRepositoryPort books) {
        return new BookService(books);
    }

    @Bean
    LoanService loanService(BookRepositoryPort books, LoanRepositoryPort loans, ClockPort clock) {
        return new LoanService(books, loans, clock);
    }
}
