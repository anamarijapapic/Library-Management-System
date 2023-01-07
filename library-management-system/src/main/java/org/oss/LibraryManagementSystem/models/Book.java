package org.oss.LibraryManagementSystem.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import javax.validation.constraints.NotNull;
import jakarta.persistence.*;
import lombok.*;
import org.oss.LibraryManagementSystem.models.enums.Status;
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
    private int id;

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

}
