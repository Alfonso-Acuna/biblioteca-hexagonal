package com.biblioteca.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLoanRepository extends JpaRepository<LoanEntity, Long> { }
