package org.oss.LibraryManagementSystem.controllers;

import jakarta.mail.MessagingException;
import org.oss.LibraryManagementSystem.dto.UserPayload;
import org.oss.LibraryManagementSystem.repositories.RoleRepository;
import org.oss.LibraryManagementSystem.repositories.UserRepository;
import org.oss.LibraryManagementSystem.services.UserService;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserController(UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping
    public String getAllUsers(Authentication authentication, Model model, @RequestParam(required = false) String keyword, @RequestParam(required = false) String roleName, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "id,asc") String[] sort) {
        var userPage = userService.getAllUsers(authentication, keyword, roleName, page, size, sort);
        var users = userPage.getContent();
        var currentUser = userRepository.findByEmail(authentication.getName());

        var currentUserRoles = new ArrayList<String>();
        for (var role : currentUser.getRoles()) {
            currentUserRoles.add(role.getName());
        }
        var roles = roleRepository.findAll();

        var sortField = sort[0];
        var sortDirection = sort[1];

        model.addAttribute("users", users);
        model.addAttribute("currentUserRole", currentUserRoles.get(0));

        model.addAttribute("roleOptions", roles);

        model.addAttribute("currentPage", userPage.getNumber() + 1);
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("pageSize", size);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        if (keyword != null) model.addAttribute("keyword", keyword);
        if (roleName != null) model.addAttribute("roleName", roleName);

        return "user/allUsers";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/{id}")
    public String userDetails(Model model, @PathVariable Integer id) {
        var user = userService.getUser(id);

        if (user != null) model.addAttribute("user", user);

        return "user/userDetails";
    }

    @GetMapping("/myDetails")
    public String userDetails(Model model) {
        var user = userService.currentUserDetails();

        model.addAttribute("user", user);

        return "user/userDetails";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUserById(id);
            redirectAttributes.addFlashAttribute("message", "The user with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/users";
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("/add")
    public String addNewUser(Model model, UserPayload userPayload) {
        var roles = roleRepository.findAll();

        model.addAttribute("userPayload", userPayload);
        model.addAttribute("roleOptions", roles);

        return "user/addNewUser";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/saveUser")
    public RedirectView saveNewUser(@ModelAttribute("userPayload") UserPayload userPayload) throws ParseException, MessagingException {
        var user = userService.createUser(userPayload);
        userRepository.save(user);

        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost("mailhog");
        mailSender.setPort(1025);
        mailSender.setUsername("");
        mailSender.setPassword("");

        var messageText = "<h1>Welcome</h1>" + "<p>" + user.getFirstName() + ' ' + user.getLastName() + ", welcome to Library Management System!" + "</p>" + "<h5>Happy reading!</h5>";

        var props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");

        var mimeMessage = mailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("librarymanagementsystem@oss.org");
        mimeMessageHelper.setText(messageText, true);
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setSubject("Welcome to Library Management System");

        mailSender.send(mimeMessage);

        return new RedirectView("/users");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @GetMapping("{id}/edit")
    public String editUser(@PathVariable("id") Integer id, Authentication authentication, Model model) {
        var user = userRepository.findById(id).orElse(null);
        var currentUserRoles = authentication.getAuthorities();

        var roles = roleRepository.findAll();
        var roleUser = user.getRoles().stream().toList().get(0).getName();
        if (currentUserRoles.contains(new SimpleGrantedAuthority("LIBRARIAN")) && user.getRoles().contains(roleRepository.findByName("MEMBER")) || currentUserRoles.contains(new SimpleGrantedAuthority("ADMIN"))) {
            model.addAttribute("userPayload", new UserPayload(user.getId(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getEmail(), new Date(user.getDateOfBirth().getTime()), roleUser, user.getContactNumber()));
            model.addAttribute("roleOptions", roles);
        }

        return "user/editUser";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'LIBRARIAN')")
    @PostMapping("/updateUser")
    public RedirectView updateUser(@ModelAttribute("userPayload") UserPayload userPayload) throws ParseException {
        var user = userService.editUser(userPayload.getId(), userPayload);
        userRepository.save(user);

        return new RedirectView("/users");
    }

}
