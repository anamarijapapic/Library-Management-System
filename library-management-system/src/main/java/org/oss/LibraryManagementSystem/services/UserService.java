package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.models.User;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<User> getAllUsers (String keyword, int page, int size);
}
