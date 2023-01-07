package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
}
