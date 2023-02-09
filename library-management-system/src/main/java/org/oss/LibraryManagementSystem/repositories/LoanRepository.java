package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Book;
import org.oss.LibraryManagementSystem.models.Loan;
import org.oss.LibraryManagementSystem.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    Page<Loan> findAll(Pageable pageable);

    Page<Loan> findByBookId(Integer bookId, Pageable pageable);

    Page<Loan> findByMemberIdAndDateReturned(Integer memberId, Timestamp dateReturned, Pageable pageable);

    Page<Loan> findByMemberIdAndDateReturnedBefore(Integer memberId, Timestamp dateReturned, Pageable pageable);

    Loan findTopByBookOrderByDateIssuedDesc(Book book);

    long countByMemberAndDateReturned(User member, Timestamp dateReturned);

}
