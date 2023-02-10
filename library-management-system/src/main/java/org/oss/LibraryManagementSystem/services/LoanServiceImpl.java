package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.LoanPayload;
import org.oss.LibraryManagementSystem.models.Loan;
import org.oss.LibraryManagementSystem.repositories.BookRepository;
import org.oss.LibraryManagementSystem.repositories.LoanRepository;
import org.oss.LibraryManagementSystem.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class LoanServiceImpl implements LoanService {

    private final long MAX_LOANS = 5L;

    public LoanRepository loanRepository;

    public UserRepository userRepository;

    public BookRepository bookRepository;

    public LoanServiceImpl(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Loan createLoan(Integer bookId, LoanPayload loanPayload) {
        var book = bookRepository.findById(bookId).orElse(null);

        var lastLoan = loanRepository.findTopByBookOrderByDateIssuedDesc(book);
        var bookAlreadyInLoan = lastLoan != null && lastLoan.getDateReturned() == null;

        var member = userRepository.findById(loanPayload.getMember()).orElse(null);

        var numOfCurrentMemberLoans = loanRepository.countByMemberAndDateReturned(member, null);

        if (book != null && book.getBookStatus().name().equals("OK") && !bookAlreadyInLoan && numOfCurrentMemberLoans < MAX_LOANS) {
            var loan = new Loan();

            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var librarian = userRepository.findByEmail(authentication.getName());
            var dateIssued = new Timestamp(System.currentTimeMillis());

            loan.setMember(member);
            loan.setLibrarian(librarian);
            loan.setDateIssued(dateIssued);
            loan.setDateReturned(null);
            loan.setBook(book);

            book.setAvailable(false);
            bookRepository.save(book);

            return loan;
        }

        return null;
    }

    @Override
    public Loan endLoan(Integer bookId) {
        var book = bookRepository.findById(bookId).orElse(null);

        var loan = loanRepository.findTopByBookOrderByDateIssuedDesc(book);

        if (book != null && loan != null && loan.getDateReturned() == null) {
            var dateReturned = new Timestamp(System.currentTimeMillis());

            loan.setDateReturned(dateReturned);

            book.setAvailable(true);
            bookRepository.save(book);

            return loan;
        }

        return null;
    }

    @Override
    public Page<Loan> getAllLoans(int page, int size, String[] sort) {
        var sortField = sort[0];
        var sortDirection = sort[1];
        var direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        var order = new Sort.Order(direction, sortField);

        Pageable paging = PageRequest.of(page - 1, size, Sort.by(order));

        return loanRepository.findAll(paging);
    }

    @Override
    public Page<Loan> getCurrentLoans(Integer memberId, int page, int size, String[] sort) {
        var sortField = sort[0];
        var sortDirection = sort[1];
        var direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        var order = new Sort.Order(direction, sortField);

        Pageable paging = PageRequest.of(page - 1, size, Sort.by(order));

        return loanRepository.findByMemberIdAndDateReturned(memberId, null, paging);
    }

    @Override
    public Page<Loan> getPreviousLoans(Integer memberId, int page, int size, String[] sort) {
        var sortField = sort[0];
        var sortDirection = sort[1];
        var direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        var order = new Sort.Order(direction, sortField);

        Pageable paging = PageRequest.of(page - 1, size, Sort.by(order));

        var now = new Timestamp(System.currentTimeMillis());

        return loanRepository.findByMemberIdAndDateReturnedBefore(memberId, now, paging);
    }

    @Override
    public Page<Loan> getLoansByBookId(Integer bookId, int page, int size, String[] sort) {
        var sortField = sort[0];
        var sortDirection = sort[1];
        var direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        var order = new Sort.Order(direction, sortField);

        Pageable paging = PageRequest.of(page - 1, size, Sort.by(order));

        return loanRepository.findByBookId(bookId, paging);
    }

}
