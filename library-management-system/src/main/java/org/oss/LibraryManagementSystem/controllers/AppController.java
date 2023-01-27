package org.oss.LibraryManagementSystem.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/")
    public String viewHomePage() {
        return "index";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public String viewAdminPage() {
        return "admin";
    }

}
