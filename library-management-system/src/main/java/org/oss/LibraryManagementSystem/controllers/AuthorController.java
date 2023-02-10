package org.oss.LibraryManagementSystem.controllers;

import org.oss.LibraryManagementSystem.dto.AuthorPayload;
import org.oss.LibraryManagementSystem.repositories.AuthorRepository;
import org.oss.LibraryManagementSystem.services.AuthorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorService authorService, AuthorRepository authorRepository) {
        this.authorService = authorService;
        this.authorRepository = authorRepository;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/add")
    public String addNewAuthor(Model model, AuthorPayload authorPayload) {
        model.addAttribute("authorPayload", authorPayload);

        return "author/addNewAuthor";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/saveAuthor")
    public RedirectView saveNewAuthor(@ModelAttribute("authorPayload") AuthorPayload authorPayload) {
        var author = authorService.createAuthor(authorPayload);
        authorRepository.save(author);

        return new RedirectView("/authors");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping
    public String getAllAuthors(Model model, @RequestParam(required = false) String keyword, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "id,asc") String[] sort) {
        var pageAuthors = authorService.getAllAuthors(keyword, page, size, sort);
        var authors = pageAuthors.getContent();

        var sortField = sort[0];
        var sortDirection = sort[1];

        model.addAttribute("authors", authors);

        model.addAttribute("currentPage", pageAuthors.getNumber() + 1);
        model.addAttribute("totalItems", pageAuthors.getTotalElements());
        model.addAttribute("totalPages", pageAuthors.getTotalPages());
        model.addAttribute("pageSize", size);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        if (keyword != null) model.addAttribute("keyword", keyword);

        return "author/allAuthors";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/delete")
    public String deleteAuthor(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            authorService.deleteAuthorById(id);
            redirectAttributes.addFlashAttribute("message", "The author with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/authors";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("{id}/edit")
    public String editAuthor(@PathVariable("id") Integer id, Model model) {
        var author = authorRepository.findById(id).orElse(null);

        model.addAttribute("authorPayload", new AuthorPayload(author.getId(), author.getFirstName(), author.getLastName()));

        return "author/editAuthor";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/updateAuthor")
    public RedirectView updateAuthor(@ModelAttribute("authorPayload") AuthorPayload authorPayload) {
        var author = authorService.editAuthor(authorPayload.getId(), authorPayload);
        authorRepository.save(author);

        return new RedirectView("/authors");
    }

}
