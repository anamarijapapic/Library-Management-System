package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
