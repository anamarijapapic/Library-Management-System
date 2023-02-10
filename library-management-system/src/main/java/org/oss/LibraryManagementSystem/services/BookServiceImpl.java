package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.BookPayload;
import org.oss.LibraryManagementSystem.models.Book;
import org.oss.LibraryManagementSystem.models.enums.Status;
import org.oss.LibraryManagementSystem.repositories.BookRepository;
import org.oss.LibraryManagementSystem.repositories.LoanRepository;
import org.oss.LibraryManagementSystem.repositories.WorkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final WorkRepository workRepository;

    private final LoanRepository loanRepository;

    public BookServiceImpl(BookRepository bookRepository, WorkRepository workRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.workRepository = workRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public Book createBook(BookPayload bookPayload) throws ParseException {
        var book = new Book();

        var work = workRepository.findById(bookPayload.getWork()).orElse(null);

        book.setWork(work);
        book.setPublisherName(bookPayload.getPublisherName());

        var date = bookPayload.getYearOfPublishing();
        var timestamp = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(date.toString()).getTime());

        book.setYearOfPublishing(timestamp);
        book.setIsbn(bookPayload.getIsbn());

        var statusStr = bookPayload.getBookStatus();
        var status = Status.valueOf(statusStr);
        book.setBookStatus(status);

        book.setAvailable(true);

        return book;
    }

    @Override
    public Page<Book> getAllBooks(String keyword, String statusName, int page, int size) {
        Pageable paging = PageRequest.of(page - 1, size);

        Page<Book> bookPage;

        if (keyword != null && statusName != null && !statusName.equals("All statuses")) {
            bookPage = bookRepository.findBooksByBookStatusAndPublisherNameContainingIgnoreCase(statusName, keyword, paging);
        } else if (statusName != null && !statusName.equals("All statuses")) {
            bookPage = bookRepository.findBooksByBookStatus(statusName, paging);
        } else if (keyword != null) {
            bookPage = bookRepository.findByIsbnContainingOrPublisherNameContainingAllIgnoreCase(keyword, keyword, paging);
        } else {
            bookPage = bookRepository.findAll(paging);
        }

        return bookPage;
    }

    @Override
    public void deleteBookById(Integer id) {
        bookRepository.findById(id).ifPresent(bookRepository::delete);
    }


    @Override
    public Book editBook(Integer id, BookPayload bookPayload) throws ParseException {
        var book = bookRepository.findById(id).orElse(null);

        book.setId(bookPayload.getId());
        book.setWork(workRepository.findById(bookPayload.getWork()).orElse(null));
        book.setPublisherName(bookPayload.getPublisherName());

        var date = bookPayload.getYearOfPublishing();
        var timestamp = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(date.toString()).getTime());

        book.setYearOfPublishing(timestamp);
        book.setIsbn(bookPayload.getIsbn());

        var statusStr = bookPayload.getBookStatus();
        var status = Status.valueOf(statusStr);
        book.setBookStatus(status);

        book.setAvailable(true);

        return book;
    }

}
