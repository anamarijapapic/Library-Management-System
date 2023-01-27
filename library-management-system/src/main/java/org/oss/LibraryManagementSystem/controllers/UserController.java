package org.oss.LibraryManagementSystem.controllers;

import org.oss.LibraryManagementSystem.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Model model, @RequestParam (required = false) String keyword, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "1") int size) {
        var userPage = userService.getAllUsers(keyword, page, size);
        var users = userPage.getContent();
        model.addAttribute("users", users);
        model.addAttribute("currentPage", userPage.getNumber() + 1);
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("pageSize", size);
        if (keyword != null)
            model.addAttribute("keyword", keyword);
        return "user/allUsers";
    }

}
