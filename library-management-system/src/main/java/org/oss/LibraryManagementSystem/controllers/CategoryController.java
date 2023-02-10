package org.oss.LibraryManagementSystem.controllers;

import org.oss.LibraryManagementSystem.dto.CategoryPayload;
import org.oss.LibraryManagementSystem.repositories.CategoryRepository;
import org.oss.LibraryManagementSystem.services.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping
    public String getAllCategories(Model model, @RequestParam(required = false) String keyword, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "id,asc") String[] sort) {
        var pageCategory = categoryService.getAllCategories(keyword, page, size, sort);
        var categories = pageCategory.getContent();

        var sortField = sort[0];
        var sortDirection = sort[1];

        model.addAttribute("categories", categories);

        model.addAttribute("currentPage", pageCategory.getNumber() + 1);
        model.addAttribute("totalItems", pageCategory.getTotalElements());
        model.addAttribute("totalPages", pageCategory.getTotalPages());
        model.addAttribute("pageSize", size);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        if (keyword != null) model.addAttribute("keyword", keyword);

        return "category/allCategories";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/add")
    public String addNewCategory(Model model, CategoryPayload categoryPayload) {
        model.addAttribute("categoryPayload", categoryPayload);

        return "category/addNewCategory";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/saveCategory")
    public RedirectView saveNewCategory(@ModelAttribute("categoryPayload") CategoryPayload categoryPayload) {
        var category = categoryService.createCategory(categoryPayload);
        categoryRepository.save(category);

        return new RedirectView("/categories");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/delete")
    public String deleteCategory(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteCategoryById(id);
            redirectAttributes.addFlashAttribute("message", "The category with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/categories";
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("{id}/edit")
    public String editCategory(@PathVariable("id") Integer id, Model model) {
        var category = categoryRepository.findById(id).orElse(null);

        model.addAttribute("categoryPayload", new CategoryPayload(category.getId(), category.getName()));

        return "category/editCategory";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/updateCategory")
    public RedirectView updateCategory(@ModelAttribute("categoryPayload") CategoryPayload categoryPayload) {
        var category = categoryService.editCategory(categoryPayload.getId(), categoryPayload);
        categoryRepository.save(category);

        return new RedirectView("/categories");
    }

}
