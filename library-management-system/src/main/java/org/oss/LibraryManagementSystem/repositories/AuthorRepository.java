package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Page<Author> findAll (Pageable pageable);

    Page<Author> findByFirstNameContainingOrLastNameContaining (String firstName, String lastName, Pageable pageable);
}
