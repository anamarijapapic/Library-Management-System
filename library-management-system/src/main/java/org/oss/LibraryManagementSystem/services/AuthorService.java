package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.AuthorPayload;
import org.oss.LibraryManagementSystem.models.Author;
import org.springframework.data.domain.Page;

public interface AuthorService {

    Author createAuthor(AuthorPayload authorPayload);

    Page<Author> getAllAuthors(String keyword, int page, int size, String[] sort);

    void deleteAuthorById(Integer id);

    Author editAuthor(Integer id, AuthorPayload authorPayload);

}
