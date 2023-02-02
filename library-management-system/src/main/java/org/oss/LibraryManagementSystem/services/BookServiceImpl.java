package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.BookPayload;
import org.oss.LibraryManagementSystem.dto.WorkPayload;
import org.oss.LibraryManagementSystem.models.Author;
import org.oss.LibraryManagementSystem.models.Book;
import org.oss.LibraryManagementSystem.models.Category;
import org.oss.LibraryManagementSystem.models.Work;
import org.oss.LibraryManagementSystem.models.enums.Status;
import org.oss.LibraryManagementSystem.repositories.BookRepository;
import org.oss.LibraryManagementSystem.repositories.WorkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

@Service
public class BookServiceImpl  implements BookService{
    private final BookRepository bookRepository;

    private final WorkRepository workRepository;

    public BookServiceImpl (BookRepository bookRepository, WorkRepository workRepository) {
        this.bookRepository = bookRepository;
        this.workRepository = workRepository;
    }

    @Override
    public Book createBook (BookPayload bookPayload) throws ParseException {
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

        return book;
    }

    @Override
    public Page<Book> getAllBooks(String keyword, int page, int size, String[] sort) {
        String sortField = sort[0];
        String sortDirection = sort[1];
        Sort.Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort.Order order = new Sort.Order(direction, sortField);
        Pageable paging = PageRequest.of(page - 1, size, Sort.by(order));
        Page<Book> bookPage;
        if (keyword == null) {
            bookPage = bookRepository.findAll(paging);
        } else {
            bookPage = bookRepository.findByIsbnContainingOrPublisherNameContaining(keyword, keyword, paging);
        }
        return bookPage;
    }

    @Override
    public void deleteBookById(Integer id) {
        bookRepository.findById(id).ifPresent(bookRepository::delete);
    }


    @Override
    public Book editBook (Integer id, BookPayload bookPayload) throws ParseException{
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

        return book;
    }
}
