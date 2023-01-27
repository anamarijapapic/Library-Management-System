package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    Page<User> findAll (Pageable pageable);

    Page<User> findByEmailContaining(String email, Pageable pageable);

}
