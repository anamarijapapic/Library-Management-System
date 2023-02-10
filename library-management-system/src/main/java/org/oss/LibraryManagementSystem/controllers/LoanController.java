package org.oss.LibraryManagementSystem.controllers;

import jakarta.mail.MessagingException;
import org.oss.LibraryManagementSystem.dto.LoanPayload;
import org.oss.LibraryManagementSystem.repositories.BookRepository;
import org.oss.LibraryManagementSystem.repositories.LoanRepository;
import org.oss.LibraryManagementSystem.repositories.RoleRepository;
import org.oss.LibraryManagementSystem.repositories.UserRepository;
import org.oss.LibraryManagementSystem.services.LoanService;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/loans")
public class LoanController {

    private final LoanRepository loanRepository;

    private final LoanService loanService;

    private final UserRepository userRepository;

    private final BookRepository bookRepository;

    private final RoleRepository roleRepository;

    public LoanController(LoanRepository loanRepository, LoanService loanService, UserRepository userRepository, BookRepository bookRepository, RoleRepository roleRepository) {
        this.loanRepository = loanRepository;
        this.loanService = loanService;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.roleRepository = roleRepository;

    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/{bookId}/start")
    public String addNewLoan(@PathVariable("bookId") Integer bookId, LoanPayload loanPayload, Model model) {
        var roleMember = roleRepository.findByName("MEMBER");
        var members = userRepository.findByRoles(roleMember);
        var book = bookRepository.findById(bookId).orElse(null);
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var librarian = userRepository.findByEmail(authentication.getName());

        model.addAttribute("librarian", librarian);
        model.addAttribute("memberOptions", members);
        model.addAttribute("loanPayload", loanPayload);
        model.addAttribute("book", book);

        return "loan/startNewLoan";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/{bookId}/saveLoan")
    public RedirectView saveNewLoan(@PathVariable("bookId") Integer bookId, @ModelAttribute("loanPayload") LoanPayload loanPayload) throws MessagingException {
        var loan = loanService.createLoan(bookId, loanPayload);
        loanRepository.save(loan);

        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost("mailhog");
        mailSender.setPort(1025);
        mailSender.setUsername("");
        mailSender.setPassword("");

        var messageText = "<h1>Loan Started</h1>" + "<p>" + "Book <b>" + loan.getBook().getWork().getTitle() + "</b>" + " was issued on your name on date <b>" + loan.getDateIssued() + "</b>." + "</p>";

        var props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");

        var mimeMessage = mailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("librarymanagementsystem@oss.org");
        mimeMessageHelper.setText(messageText, true);
        mimeMessageHelper.setTo(loan.getMember().getEmail());
        mimeMessageHelper.setSubject("Loan Started");

        mailSender.send(mimeMessage);

        return new RedirectView("/loans");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/{bookId}/end")
    public String endLoan(@PathVariable("bookId") Integer bookId) throws MessagingException {
        var loan = loanService.endLoan(bookId);
        loanRepository.save(loan);

        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost("mailhog");
        mailSender.setPort(1025);
        mailSender.setUsername("");
        mailSender.setPassword("");

        var messageText = "<h1>Loan Ended</h1>" + "<p>" + "You returned book <b>" + loan.getBook().getWork().getTitle() + "</b>" + " on date <b>" + loan.getDateReturned() + "</b>." + "</p>";

        var props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");

        var mimeMessage = mailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("librarymanagementsystem@oss.org");
        mimeMessageHelper.setText(messageText, true);
        mimeMessageHelper.setTo(loan.getMember().getEmail());
        mimeMessageHelper.setSubject("Loan Ended");

        mailSender.send(mimeMessage);

        return "redirect:/loans";
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping
    public String getAllLoans(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "id,asc") String[] sort) {
        var pageLoans = loanService.getAllLoans(page, size, sort);
        var loans = pageLoans.getContent();

        var sortField = sort[0];
        var sortDirection = sort[1];

        model.addAttribute("loans", loans);
        model.addAttribute("loanType", "all");

        model.addAttribute("currentPage", pageLoans.getNumber() + 1);
        model.addAttribute("totalItems", pageLoans.getTotalElements());
        model.addAttribute("totalPages", pageLoans.getTotalPages());
        model.addAttribute("pageSize", size);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        return "loan/allLoans";
    }

    @PreAuthorize("hasAuthority('MEMBER')")
    @GetMapping("/myLoans")
    public String myLoans(Model model) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findByEmail(authentication.getName());

        model.addAttribute("user", user);

        return "loan/myLoans";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN') or @userServiceImpl.findById(#memberId).id == @userServiceImpl.findByEmail(authentication.name).id")
    @GetMapping("/{memberId}/current")
    public String getCurrentLoans(@PathVariable("memberId") Integer memberId, Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "id,asc") String[] sort) {
        var pageLoans = loanService.getCurrentLoans(memberId, page, size, sort);
        var loans = pageLoans.getContent();
        var member = userRepository.findById(memberId).orElse(null);

        var sortField = sort[0];
        var sortDirection = sort[1];

        model.addAttribute("loans", loans);
        model.addAttribute("loanType", "current");
        model.addAttribute("member", member);

        model.addAttribute("currentPage", pageLoans.getNumber() + 1);
        model.addAttribute("totalItems", pageLoans.getTotalElements());
        model.addAttribute("totalPages", pageLoans.getTotalPages());
        model.addAttribute("pageSize", size);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        return "loan/allLoans";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN') or @userServiceImpl.findById(#memberId).id == @userServiceImpl.findByEmail(authentication.name).id")
    @GetMapping("/{memberId}/previous")
    public String getPreviousLoans(@PathVariable("memberId") Integer memberId, Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "id,asc") String[] sort) {
        var pageLoans = loanService.getPreviousLoans(memberId, page, size, sort);
        var loans = pageLoans.getContent();
        var member = userRepository.findById(memberId).orElse(null);

        var sortField = sort[0];
        var sortDirection = sort[1];

        model.addAttribute("loans", loans);
        model.addAttribute("loanType", "previous");
        model.addAttribute("member", member);

        model.addAttribute("currentPage", pageLoans.getNumber() + 1);
        model.addAttribute("totalItems", pageLoans.getTotalElements());
        model.addAttribute("totalPages", pageLoans.getTotalPages());
        model.addAttribute("pageSize", size);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        return "loan/allLoans";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/book/{bookId}")
    public String getLoansByBook(@PathVariable("bookId") Integer bookId, Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "id,asc") String[] sort) {
        var pageLoans = loanService.getLoansByBookId(bookId, page, size, sort);
        var loans = pageLoans.getContent();
        var book = bookRepository.findById(bookId).orElse(null);

        var sortField = sort[0];
        var sortDirection = sort[1];

        model.addAttribute("loans", loans);
        model.addAttribute("book", book);

        model.addAttribute("currentPage", pageLoans.getNumber() + 1);
        model.addAttribute("totalItems", pageLoans.getTotalElements());
        model.addAttribute("totalPages", pageLoans.getTotalPages());
        model.addAttribute("pageSize", size);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        return "loan/allLoans";
    }

}
