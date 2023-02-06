package org.oss.LibraryManagementSystem.models;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    private User member;

    @ManyToOne
    @JoinColumn(name = "librarian_id", referencedColumnName = "id", nullable = false)
    private User librarian;

    @Column(name = "date_issued")
    @NotNull(message = "date_issued shouldn't be null")
    private Timestamp dateIssued;

    @Column(name = "date_returned")
    private Timestamp dateReturned;

    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
    private Book book;

}
