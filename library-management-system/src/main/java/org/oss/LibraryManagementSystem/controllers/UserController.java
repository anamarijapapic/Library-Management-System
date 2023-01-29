package org.oss.LibraryManagementSystem.controllers;

import org.oss.LibraryManagementSystem.repositories.UserRepository;
import org.oss.LibraryManagementSystem.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;


@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('LIBRARIAN')")
    @GetMapping
    public String getAllUsers(Authentication authentication, Model model,
                              @RequestParam (required = false) String keyword,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "3") int size,
                              @RequestParam(defaultValue = "id,asc") String[] sort) {
        var userPage = userService.getAllUsers(authentication, keyword, page, size, sort);
        var users = userPage.getContent();
        var currentUser = userRepository.findByEmail(authentication.getName());
        var currentUserRoles = new ArrayList<String>();
        for (var role : currentUser.getRoles()){
            currentUserRoles.add(role.getName());
        }
        var sortField = sort[0];
        var sortDirection = sort[1];
        model.addAttribute("currentUserRole", currentUserRoles.get(0));
        model.addAttribute("users", users);
        model.addAttribute("currentPage", userPage.getNumber() + 1);
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("sortField", sortField );
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        if (keyword != null)
            model.addAttribute("keyword", keyword);
        return "user/allUsers";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('LIBRARIAN')")
    @GetMapping("/{id}")
    public String userDetails(Model model, @PathVariable Integer id){
        var user = userService.getUser(id);
        if (user != null)
            model.addAttribute("user", user);
        return "user/userDetails";
    }

    @GetMapping("/details")
    public String userDetails(Model model){
        var user = userService.currentUserDetails();
        model.addAttribute("user", user);
        return "user/userDetails";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/delete")
    public String deleteUser (@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUserById(id);
            redirectAttributes.addFlashAttribute("message", "The Tutorial with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/user";
    }
}
