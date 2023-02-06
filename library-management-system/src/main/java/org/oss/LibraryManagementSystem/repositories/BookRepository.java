package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Book;
import org.oss.LibraryManagementSystem.models.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Page<Book> findAll (Pageable pageable);

    Page<Book> findByIsbnContainingOrPublisherNameContaining (String isbn , String publisherName, Pageable pageable);

    Page<Book> findByWork (Work work, Pageable pageable);

    Page<Book> findByWorkAndPublisherNameContainingIgnoreCase (Work work, String publisherName, Pageable pageable);
}
