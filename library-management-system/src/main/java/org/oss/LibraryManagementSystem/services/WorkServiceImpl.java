package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.WorkPayload;
import org.oss.LibraryManagementSystem.models.*;
import org.oss.LibraryManagementSystem.repositories.AuthorRepository;
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

    public WorkServiceImpl(WorkRepository workRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.workRepository = workRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<Work> getAllWorks(String keyword, String categoryName, int page, int size, String[] sort) {
        String sortField = sort[0];
        String sortDirection = sort[1];
        Sort.Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort.Order order = new Sort.Order(direction, sortField);
        Pageable paging = PageRequest.of(page - 1, size, Sort.by(order));
        Page<Work> workPage;
        Category category = null;
        if (keyword != null && categoryName != null && !categoryName.equals("All categories")) {
            category = categoryRepository.findByName(categoryName);
            workPage = workRepository.findByCategoriesEqualsAndTitleContainingIgnoreCase(category, keyword, paging);

        } else if (categoryName != null && !categoryName.equals("All categories")) {
            category = categoryRepository.findByName(categoryName);
            workPage = workRepository.findByCategoriesEquals(category, paging);

        } else if (keyword != null){
            workPage = workRepository.findByTitleContainingIgnoreCase(keyword, paging);
        }
        else {
            workPage = workRepository.findAll(paging);
        }
        return workPage;
    }

    @Override
    public void deleteWorkById(Integer id) {
        workRepository.findById(id).ifPresent(workRepository::delete);
    }

    @Override
    public Work createWork (WorkPayload workPayload){
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
    public Work editWork (Integer id, WorkPayload workPayload){
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
