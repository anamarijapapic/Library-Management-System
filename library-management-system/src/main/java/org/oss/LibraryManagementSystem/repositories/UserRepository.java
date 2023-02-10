package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Role;
import org.oss.LibraryManagementSystem.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(Integer id);

    User findByEmail(String email);

    Page<User> findAll(Pageable pageable);

    Page<User> findByEmailContainingOrFirstNameContainingOrLastNameContainingAllIgnoreCase(String email, String firstName, String lastName, Pageable pageable);

    Page<User> findByRolesEquals(Role role, Pageable pageable);

    List<User> findByRoles(Role role);

    Page<User> findByRolesEqualsAndEmailContainingIgnoreCase(Role role, String email, Pageable pageable);

}
