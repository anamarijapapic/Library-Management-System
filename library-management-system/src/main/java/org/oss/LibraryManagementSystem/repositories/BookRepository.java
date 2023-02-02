package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Page<Book> findAll (Pageable pageable);

    Page<Book> findByIsbnContainingOrPublisherNameContaining (String isbn , String publisherName, Pageable pageable);
}
