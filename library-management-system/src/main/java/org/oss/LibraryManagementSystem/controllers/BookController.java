package org.oss.LibraryManagementSystem.controllers;

import org.oss.LibraryManagementSystem.dto.BookPayload;
import org.oss.LibraryManagementSystem.models.enums.Status;
import org.oss.LibraryManagementSystem.repositories.BookRepository;
import org.oss.LibraryManagementSystem.repositories.WorkRepository;
import org.oss.LibraryManagementSystem.services.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    private final BookRepository bookRepository;

    private final WorkRepository workRepository;

    public BookController(BookService bookService, BookRepository bookRepository, WorkRepository workRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.workRepository = workRepository;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/add")
    public String addNewBook(Model model, BookPayload bookPayload) {
        var works = workRepository.findAll();

        var statusList = new ArrayList<String>();
        statusList.add(Status.OK.name());
        statusList.add(Status.LOST.name());
        statusList.add(Status.DAMAGED.name());

        model.addAttribute("bookPayload", bookPayload);
        model.addAttribute("workOptions", works);
        model.addAttribute("statusList", statusList);

        return "book/addNewBook";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/saveBook")
    public RedirectView saveNewBook(@ModelAttribute("bookPayload") BookPayload bookPayload) throws ParseException {
        var book = bookService.createBook(bookPayload);
        bookRepository.save(book);

        return new RedirectView("/books");
    }

    @GetMapping
    public String getAllBooks(Model model, @RequestParam(required = false) String keyword, @RequestParam(required = false) String statusName, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size) {
        var pageBooks = bookService.getAllBooks(keyword, statusName, page, size);
        var books = pageBooks.getContent();
        var statusOptions = Status.values();

        model.addAttribute("books", books);
        model.addAttribute("statusOptions", statusOptions);

        model.addAttribute("currentPage", pageBooks.getNumber() + 1);
        model.addAttribute("totalItems", pageBooks.getTotalElements());
        model.addAttribute("totalPages", pageBooks.getTotalPages());
        model.addAttribute("pageSize", size);

        if (keyword != null) model.addAttribute("keyword", keyword);
        if (statusName != null) model.addAttribute("statusName", statusName);

        return "book/allBooks";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBookById(id);
            redirectAttributes.addFlashAttribute("message", "The book with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/books";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("{id}/edit")
    public String editBook(@PathVariable("id") Integer id, Model model) {
        var book = bookRepository.findById(id).orElse(null);
        var works = workRepository.findAll();

        var statusList = new ArrayList<String>();
        statusList.add(Status.OK.name());
        statusList.add(Status.LOST.name());
        statusList.add(Status.DAMAGED.name());

        model.addAttribute("bookPayload", new BookPayload(book.getId(), book.getWork().getId(), book.getPublisherName(), new Date(book.getYearOfPublishing().getTime()), book.getIsbn(), book.getBookStatus().name()));
        model.addAttribute("worksOptions", works);
        model.addAttribute("statusOptions", statusList);

        return "book/editBook";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/updateBook")
    public RedirectView updateBook(@ModelAttribute("bookPayload") BookPayload bookPayload) throws ParseException {
        var book = bookService.editBook(bookPayload.getId(), bookPayload);
        bookRepository.save(book);

        return new RedirectView("/books");
    }

}
