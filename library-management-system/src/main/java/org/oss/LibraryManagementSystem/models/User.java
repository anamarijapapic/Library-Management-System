package org.oss.LibraryManagementSystem.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@Table(name = "\"user\"", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
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

    @Column(name = "password")
    @NotNull(message = "password shouldn't be null")
    private String password;

    @Column(name = "email")
    @NotNull(message = "email shouldn't be null")
    private String email;

    @Column(name = "date_of_birth")
    @NotNull(message = "date_of_birth shouldn't be null")
    private Timestamp dateOfBirth;

    @Column(name = "contact_number")
    @NotNull(message = "contact_number shouldn't be null")
    private String contactNumber;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(String firstName, String lastName, String password, String email, Timestamp dateOfBirth, String contactNumber, boolean enabled, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNumber;
        this.enabled = enabled;
        this.roles = roles;
    }

}
