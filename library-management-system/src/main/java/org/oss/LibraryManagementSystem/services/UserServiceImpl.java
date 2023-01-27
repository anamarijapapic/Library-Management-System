package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.models.User;
import org.oss.LibraryManagementSystem.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    public UserServiceImpl (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> getAllUsers(String keyword, int page, int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<User> pageUsers;
        if (keyword == null) {
            pageUsers = userRepository.findAll(paging);
        } else {
            pageUsers = userRepository.findByEmailContaining(keyword, paging);
        }
        return pageUsers;
    }

}
