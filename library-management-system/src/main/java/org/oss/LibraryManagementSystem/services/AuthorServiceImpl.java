package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.AuthorPayload;
import org.oss.LibraryManagementSystem.models.Author;
import org.oss.LibraryManagementSystem.repositories.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author createAuthor(AuthorPayload authorPayload) {
        var author = new Author();
        author.setFirstName(authorPayload.getFirstName());
        author.setLastName(authorPayload.getLastName());
        return author;
    }

    @Override
    public Page<Author> getAllAuthors(String keyword, int page, int size, String[] sort) {
        var sortField = sort[0];
        var sortDirection = sort[1];
        var direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        var order = new Sort.Order(direction, sortField);

        Pageable paging = PageRequest.of(page - 1, size, Sort.by(order));

        Page<Author> authorPage;

        if (keyword == null) {
            authorPage = authorRepository.findAll(paging);
        } else {
            authorPage = authorRepository.findByFirstNameContainingOrLastNameContainingAllIgnoreCase(keyword, keyword, paging);
        }

        return authorPage;
    }

    @Override
    public void deleteAuthorById(Integer id) {
        authorRepository.findById(id).ifPresent(authorRepository::delete);
    }

    @Override
    public Author editAuthor(Integer id, AuthorPayload authorPayload) {
        var author = authorRepository.findById(id).orElse(null);

        author.setId(authorPayload.getId());
        author.setFirstName(authorPayload.getFirstName());
        author.setLastName(authorPayload.getLastName());

        return author;
    }

}
