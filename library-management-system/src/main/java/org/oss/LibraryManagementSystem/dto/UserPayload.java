package org.oss.LibraryManagementSystem.dto;

import lombok.*;

import java.sql.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPayload {

    private Integer id;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private Date dateOfBirth;

    private String userRole;

    private String contactNumber;

}
