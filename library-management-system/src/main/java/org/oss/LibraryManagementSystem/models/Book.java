package org.oss.LibraryManagementSystem.models;

import jakarta.persistence.*;
import lombok.*;
import org.oss.LibraryManagementSystem.models.enums.Status;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "work_id", referencedColumnName = "id", nullable = false)
    private Work work;

    @Column(name = "publisher_name")
    @NotNull(message = "publisher_name shouldn't be null")
    private String publisherName;

    @Column(name = "year_of_publishing")
    @NotNull(message = "year_of_publishing shouldn't be null")
    private Timestamp yearOfPublishing;

    @Column(name = "isbn")
    @NotNull(message = "isbn shouldn't be null")
    private String isbn;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_status")
    private Status bookStatus;

    @Column(name = "available")
    @NotNull(message = "available shouldn't be null")
    private boolean available;

    public Book(Work work, String publisherName, Timestamp yearOfPublishing, String isbn, Status bookStatus, boolean available) {
        this.work = work;
        this.publisherName = publisherName;
        this.yearOfPublishing = yearOfPublishing;
        this.isbn = isbn;
        this.bookStatus = bookStatus;
        this.available = available;
    }

}
