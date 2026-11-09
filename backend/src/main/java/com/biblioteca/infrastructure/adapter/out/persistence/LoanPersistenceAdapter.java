package com.biblioteca.infrastructure.adapter.out.persistence;

import com.biblioteca.domain.model.Loan;
import com.biblioteca.domain.port.out.LoanRepositoryPort;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class LoanPersistenceAdapter implements LoanRepositoryPort {
    private final JpaLoanRepository jpa;

    public LoanPersistenceAdapter(JpaLoanRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Loan save(Loan loan) {
        return PersistenceMapper.toDomain(jpa.save(PersistenceMapper.toEntity(loan)));
    }

    @Override
    public Optional<Loan> findById(Long id) {
        return jpa.findById(id).map(PersistenceMapper::toDomain);
    }

    @Override
    public List<Loan> findAll() {
        return jpa.findAll().stream().map(PersistenceMapper::toDomain).toList();
    }
}
