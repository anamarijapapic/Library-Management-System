package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.CategoryPayload;
import org.oss.LibraryManagementSystem.models.Category;
import org.oss.LibraryManagementSystem.repositories.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(CategoryPayload categoryPayload) {
        var category = new Category();

        category.setName(categoryPayload.getName());

        return category;
    }

    @Override
    public Page<Category> getAllCategories(String keyword, int page, int size, String[] sort) {
        var sortField = sort[0];
        var sortDirection = sort[1];
        var direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        var order = new Sort.Order(direction, sortField);

        Pageable paging = PageRequest.of(page - 1, size, Sort.by(order));

        Page<Category> categoryPage;

        if (keyword == null) {
            categoryPage = categoryRepository.findAll(paging);
        } else {
            categoryPage = categoryRepository.findByNameContainingIgnoreCase(keyword, paging);
        }

        return categoryPage;
    }


    @Override
    public void deleteCategoryById(Integer id) {
        categoryRepository.findById(id).ifPresent(categoryRepository::delete);
    }

    @Override
    public Category editCategory(Integer id, CategoryPayload categoryPayload) {
        var category = categoryRepository.findById(id).orElse(null);

        category.setId(categoryPayload.getId());
        category.setName(categoryPayload.getName());

        return category;
    }

}
