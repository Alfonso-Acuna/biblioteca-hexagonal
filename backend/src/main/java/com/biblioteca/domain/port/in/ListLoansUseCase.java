package com.biblioteca.domain.port.in;

import com.biblioteca.domain.model.Loan;
import java.util.List;

public interface ListLoansUseCase {
    List<Loan> listAll();
}
