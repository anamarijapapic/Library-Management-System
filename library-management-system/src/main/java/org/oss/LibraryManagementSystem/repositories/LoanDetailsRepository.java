package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.LoanDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanDetailsRepository extends JpaRepository<LoanDetails, Integer> {
}
