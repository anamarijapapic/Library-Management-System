package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
