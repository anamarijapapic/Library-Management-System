package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.CategoryPayload;
import org.oss.LibraryManagementSystem.models.Category;
import org.springframework.data.domain.Page;

public interface CategoryService {

    Category createCategory(CategoryPayload categoryPayload);

    Page<Category> getAllCategories(String keyword, int page, int size, String[] sort);

    void deleteCategoryById(Integer id);

    Category editCategory(Integer id, CategoryPayload categoryPayload);

}
