package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.WorkPayload;
import org.oss.LibraryManagementSystem.models.Author;
import org.oss.LibraryManagementSystem.models.Book;
import org.oss.LibraryManagementSystem.models.Category;
import org.oss.LibraryManagementSystem.models.Work;
import org.oss.LibraryManagementSystem.repositories.AuthorRepository;
import org.oss.LibraryManagementSystem.repositories.BookRepository;
import org.oss.LibraryManagementSystem.repositories.CategoryRepository;
import org.oss.LibraryManagementSystem.repositories.WorkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class WorkServiceImpl implements WorkService {

    private final WorkRepository workRepository;

    private final AuthorRepository authorRepository;

    private final CategoryRepository categoryRepository;

    private final BookRepository bookRepository;

    public WorkServiceImpl(WorkRepository workRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, BookRepository bookRepository) {
        this.workRepository = workRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<Work> getAllWorks(String keyword, String categoryName, int page, int size, String[] sort) {
        var sortField = sort[0];
        var sortDirection = sort[1];
        var direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        var order = new Sort.Order(direction, sortField);

        Pageable paging = PageRequest.of(page - 1, size, Sort.by(order));

        Page<Work> workPage;

        Category category;

        if (keyword != null && categoryName != null && !categoryName.equals("All categories")) {
            category = categoryRepository.findByNameIgnoreCase(categoryName);
            workPage = workRepository.findByCategoriesEqualsAndTitleContainingIgnoreCase(category, keyword, paging);

        } else if (categoryName != null && !categoryName.equals("All categories")) {
            category = categoryRepository.findByNameIgnoreCase(categoryName);
            workPage = workRepository.findByCategoriesEquals(category, paging);

        } else if (keyword != null) {
            workPage = workRepository.findByTitleContainingIgnoreCase(keyword, paging);
        } else {
            workPage = workRepository.findAll(paging);
        }

        return workPage;
    }

    @Override
    public Page<Book> getBooksByWorkId(Integer workId, String keyword, String statusName, int page, int size) {
        Pageable paging = PageRequest.of(page - 1, size);

        Page<Book> bookPage;

        if (keyword != null && statusName != null && !statusName.equals("All statuses")) {
            bookPage = bookRepository.findByWorkIdAndBookStatusAndPublisherNameContainingAndIsbnContainingAllIgnoreCase(workId, statusName, keyword, keyword, paging);
        } else if (statusName != null && !statusName.equals("All statuses")) {
            bookPage = bookRepository.findByWorkIdAndBookStatus(workId, statusName, paging);
        } else if (keyword != null) {
            bookPage = bookRepository.findByWorkIdAndPublisherNameContainingIgnoreCase(workId, keyword, paging);
        } else {
            bookPage = bookRepository.findByWorkId(workId, paging);
        }

        return bookPage;
    }

    @Override
    public void deleteWorkById(Integer id) {
        workRepository.findById(id).ifPresent(workRepository::delete);
    }

    @Override
    public Work createWork(WorkPayload workPayload) {
        var work = new Work();

        work.setTitle(workPayload.getTitle());
        work.setDescription(workPayload.getDescription());

        Set<Author> authors = new HashSet<>();
        Set<Category> categories = new HashSet<>();

        var authorSet = workPayload.getAuthors();
        for (var authorId : authorSet) {
            var author = authorRepository.findById(authorId).orElse(null);
            authors.add(author);
        }

        var categorySet = workPayload.getCategories();
        for (var categoryId : categorySet) {
            var category = categoryRepository.findById(categoryId).orElse(null);
            categories.add(category);
        }

        work.setAuthors(authors);
        work.setCategories(categories);

        return work;
    }

    @Override
    public Work editWork(Integer id, WorkPayload workPayload) {
        var work = workRepository.findById(id).orElse(null);

        work.setId(workPayload.getId());
        work.setTitle(workPayload.getTitle());
        work.setDescription(workPayload.getDescription());

        Set<Author> authors = new HashSet<>();
        Set<Category> categories = new HashSet<>();

        var authorSetId = workPayload.getAuthors();
        var categorySetId = workPayload.getCategories();

        for (var authorId : authorSetId) {
            var author = authorRepository.findById(authorId).orElse(null);
            authors.add(author);
        }

        for (var categoryId : categorySetId) {
            var category = categoryRepository.findById(categoryId).orElse(null);
            categories.add(category);
        }

        work.setAuthors(authors);
        work.setCategories(categories);

        return work;
    }

}
