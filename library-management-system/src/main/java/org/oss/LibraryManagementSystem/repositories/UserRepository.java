package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Role;
import org.oss.LibraryManagementSystem.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    Page<User> findAll (Pageable pageable);

    Page<User> findByEmailContaining(String email, Pageable pageable);

    Page<User> findByEmailContainingOrFirstNameContainingOrLastNameContaining(String email, String firstName, String lastName, Pageable pageable);

    Page<User> findByRolesEquals(Role role, Pageable pageable);

    Page<User> findByRolesEqualsAndEmailContaining(Role role, String email, Pageable pageable);


}
