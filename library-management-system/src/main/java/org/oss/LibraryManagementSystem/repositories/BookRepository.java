package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Book;
import org.oss.LibraryManagementSystem.models.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Page<Book> findAll(Pageable pageable);

    Page<Book> findByIsbnContainingOrPublisherNameContainingAllIgnoreCase(String isbn , String publisherName, Pageable pageable);

    Page<Book> findByWorkAndPublisherNameContainingIgnoreCase(Work work, String publisherName, Pageable pageable);

    @Query(value = "SELECT * FROM book b WHERE CAST(b.book_status AS text) = :bookStatus", nativeQuery = true)
    Page<Book> findBooksByBookStatus(@Param("bookStatus") String bookStatus, Pageable pageable);

    @Query(value = "SELECT * FROM book b WHERE CAST(b.book_status AS text) = :bookStatus AND LOWER(b.publisher_name) LIKE LOWER(CONCAT('%',:publisherName,'%'))", nativeQuery = true)
    Page<Book> findBooksByBookStatusAndPublisherNameContainingIgnoreCase(@Param("bookStatus") String bookStatus, @Param("publisherName") String publisherName, Pageable pageable);

    @Query(value = "SELECT * FROM book b WHERE b.work_id = :workId AND CAST(b.book_status AS text) = :bookStatus", nativeQuery = true)
    Page<Book> findByWorkIdAnAndBookStatus(@Param("workId") Integer workId, @Param("bookStatus") String bookStatus, Pageable pageable);

    @Query(value = "SELECT * FROM book b WHERE b.work_id = :workId AND CAST(b.book_status AS text) = :bookStatus AND LOWER(b.publisher_name) LIKE LOWER(CONCAT('%',:publisherName,'%')) AND LOWER(b.isbn) LIKE LOWER(CONCAT('%',:isbn,'%'))", nativeQuery = true)
    Page<Book> findByWorkIdAnAndBookStatusAndPublisherNameContainingAndIsbnContainingAllIgnoreCase(@Param("workId") Integer workId, @Param("bookStatus") String bookStatus, @Param("publisherName") String publisherName, @Param("isbn") String isbn, Pageable pageable);
}
