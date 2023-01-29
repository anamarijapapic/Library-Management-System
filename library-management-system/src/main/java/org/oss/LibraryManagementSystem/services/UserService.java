package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.models.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

public interface UserService {
    Page<User> getAllUsers (Authentication authentication, String keyword, int page, int size, String[] sort);

     User getUser (Integer id);

     User currentUserDetails ();

    void deleteUserById (Integer id);

}
