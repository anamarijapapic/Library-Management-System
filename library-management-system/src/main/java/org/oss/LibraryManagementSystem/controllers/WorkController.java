package org.oss.LibraryManagementSystem.controllers;

import org.oss.LibraryManagementSystem.dto.WorkPayload;
import org.oss.LibraryManagementSystem.repositories.AuthorRepository;
import org.oss.LibraryManagementSystem.repositories.CategoryRepository;
import org.oss.LibraryManagementSystem.repositories.WorkRepository;
import org.oss.LibraryManagementSystem.services.WorkService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashSet;
import java.util.Set;


@Controller
@RequestMapping("/works")
public class WorkController {

    private final WorkService workService;

    private final WorkRepository workRepository;

    private final AuthorRepository authorRepository;

    private final CategoryRepository categoryRepository;

    public WorkController(WorkService workService, WorkRepository workRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository){
        this.workService = workService;
        this.workRepository = workRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping
    public String getAllCategories(Model model,
                                   @RequestParam(required = false) String keyword,
                                   @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "3") int size,
                                   @RequestParam(defaultValue = "id,asc") String[] sort) {
        var pageWorks = workService.getAllWorks(keyword, page, size, sort);
        var works = pageWorks.getContent();
        var sortField = sort[0];
        var sortDirection = sort[1];
        model.addAttribute("works", works);
        model.addAttribute("currentPage", pageWorks.getNumber() + 1);
        model.addAttribute("totalItems", pageWorks.getTotalElements());
        model.addAttribute("totalPages", pageWorks.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("sortField", sortField );
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        if (keyword != null)
            model.addAttribute("keyword", keyword);
        return "work/allWorks";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/add")
    public String addNewWork(Model model, WorkPayload workPayload) {
        var authors = authorRepository.findAll();
        var categories =  categoryRepository.findAll();
        model.addAttribute("workPayload", workPayload);
        model.addAttribute("authorOptions", authors);
        model.addAttribute("categoryOptions", categories);
        return "work/addNewWork";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/saveWork")
    public RedirectView saveNewWork(@ModelAttribute("workPayload") WorkPayload workPayload){
        var work = workService.createWork(workPayload);
        workRepository.save(work);
        return new RedirectView("/works");
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/delete")
    public String deleteUser (@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            workService.deleteWorkById(id);
            redirectAttributes.addFlashAttribute("message", "The work with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/works";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("{id}/edit")
    public String editWork(@PathVariable("id") Integer id, Model model) {
        var authors = authorRepository.findAll();
        var categories = categoryRepository.findAll();
        var work = workRepository.findById(id).orElse(null);
        var workAuthors = work.getAuthors();
        var workCategory = work.getCategories();
        Set<Integer> authorsId = new HashSet<>();
        Set<Integer> categoriesId = new HashSet<>();
        for (var author : workAuthors) {
            authorsId.add(author.getId());
        }
        for (var category : workCategory) {
            categoriesId.add(category.getId());
        }

        model.addAttribute("workPayload", new WorkPayload(work.getId(), work.getTitle(), work.getDescription(), authorsId, categoriesId));
        model.addAttribute("authorOptions", authors);
        model.addAttribute("categoryOptions", categories);
        return "work/editWork";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/updateWork")
    public RedirectView updateWork(@ModelAttribute("workPayload") WorkPayload workPayload){
        var work = workService.editWork(workPayload.getId(), workPayload);
        workRepository.save(work);
        return new RedirectView("/works");
    }

}
