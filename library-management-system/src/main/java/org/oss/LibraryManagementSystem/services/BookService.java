package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.BookPayload;
import org.oss.LibraryManagementSystem.models.Book;
import org.springframework.data.domain.Page;

import java.text.ParseException;

public interface BookService {

    Book createBook(BookPayload bookPayload) throws ParseException;

    Page<Book> getAllBooks(String keyword, String statusName, int page, int size);

    void deleteBookById(Integer id);

    Book editBook(Integer id, BookPayload bookPayload) throws ParseException;

}
