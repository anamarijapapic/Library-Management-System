package org.oss.LibraryManagementSystem.models;

import jakarta.persistence.*;
import lombok.*;
import org.oss.LibraryManagementSystem.models.enums.Role;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@Table(name = "\"user\"", uniqueConstraints = { @UniqueConstraint(columnNames = {"email"}) })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "first_name")
    @NotNull(message = "first_name shouldn't be null")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "last_name shouldn't be null")
    private String lastName;

    @Column(name = "passwd")
    @NotNull(message = "passwd shouldn't be null")
    private String passwd;

    @Column(name = "email")
    @NotNull(message = "email shouldn't be null")
    private String email;

    @Column(name = "date_of_birth")
    @NotNull(message = "date_of_birth shouldn't be null")
    private Timestamp dateOfBirth;

    @Column(name = "user_role")
    private String userRole;

    @Column(name = "contact_number")
    @NotNull(message = "contact_number shouldn't be null")
    private String contactNumber;

    public User(String firstName, String lastName, String passwd, String email, Timestamp dateOfBirth, String userRole, String contactNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwd = passwd;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.userRole = userRole;
        this.contactNumber = contactNumber;
    }

}
