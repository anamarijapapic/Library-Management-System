package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.UserPayload;
import org.oss.LibraryManagementSystem.models.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.text.ParseException;

public interface UserService {

    Page<User> getAllUsers(Authentication authentication, String keyword, String roleName, int page, int size, String[] sort);

    User getUser(Integer id);

    User currentUserDetails();

    void deleteUserById(Integer id);

    User createUser(UserPayload userPayload) throws ParseException;

    User editUser(Integer id, UserPayload userPayload) throws ParseException;

    User findById(Integer id);

    User findByEmail(String email);

}
